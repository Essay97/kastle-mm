package it.saggioland.kastle.service

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import it.saggioland.kastle.db.Database
import it.saggioland.kastle.error.*
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.io.path.absolutePathString
import kotlin.io.path.bufferedReader
import kotlin.io.path.bufferedWriter
import kotlin.io.path.readLines

class InstallationManager private constructor(private val gamesDbFile: Path) {

    private val db: Database

    init {
        val jdbcString = "jdbc:sqlite:${gamesDbFile.absolutePathString()}"
        val driver: SqlDriver = JdbcSqliteDriver(url = jdbcString, schema = Database.Schema)

        db = Database(driver)
    }

    val allGames: Map<String, Path>
        get() {
            val namePathMap = mutableMapOf<String, Path>()

            gamesDbFile.bufferedReader().useLines { lines ->
                for (line in lines) {
                    val (key, value) = line.split('=').map { it.trim() }
                    namePathMap[key] = Paths.get(value)
                }
            }

            return namePathMap
        }

    fun installGame(name: String, path: Path) {
        Files.write(
            gamesDbFile,
            "$name=${path.absolutePathString()}\n".toByteArray(),
            StandardOpenOption.APPEND
        )
    }

    fun uninstallGame(name: String) {
        val updatedLines = gamesDbFile.readLines().filter { line -> line.split("=")[0] != name }

        gamesDbFile.bufferedWriter().use { writer ->
            updatedLines.forEach {
                writer.write(it)
                writer.newLine()
            }
        }
    }

    fun getGameClass(name: String): Either<ConfigError, String> = either {
        val file = allGames[name]?.toFile()
        ensureNotNull(file) { GameFileError.NonExistentGame }
        // TODO remove this, it's just to make everything compilable
        "PLACEHOLDER"
    }

    companion object {
        operator fun invoke(): Either<ConfigError, InstallationManager> = either {
            val home = getUserHome().bind()
            val kastleDir = handleKastleDirectory(home).bind()
            val gamesDbFile = handleGameDbFile(kastleDir).bind()
            InstallationManager(gamesDbFile)
        }

        private fun getUserHome(): Either<UserHomeError, String> =
            Either.catch { System.getProperty("user.home") }
                .mapLeft {
                    when (it) {
                        is SecurityException -> UserHomeError.NoPermission
                        is NullPointerException -> UserHomeError.NoHomeDirectory
                        is IllegalArgumentException -> UserHomeError.EmptyProperty
                        else -> UserHomeError("Generic error when working with home directory")
                    }
                }

        private fun handleKastleDirectory(home: String): Either<KastleDirectoryError, Path> =
            Either.catch {
                val kastleFolder = Paths.get("$home/.kastle")
                if (!Files.exists(kastleFolder)) {
                    return KastleDirectoryError.CreationError.left()
                } else {
                    Files.createDirectory(kastleFolder)
                    kastleFolder
                }
            }.mapLeft {
                when (it) {
                    is SecurityException -> KastleDirectoryError.NoPermission
                    else -> KastleDirectoryError("Generic error when working with \$HOME/.kastle directory")
                }
            }

        private fun handleGameDbFile(kastleDir: Path): Either<DbFileError, Path> =
            Either.catch {
                val gamesDbFile = kastleDir.resolve("games.db")
                if (!Files.exists(gamesDbFile)) {
                    return DbFileError.CreationError.left()
                } else {
                    Files.createFile(gamesDbFile)
                    gamesDbFile
                }
            }.mapLeft {
                when (it) {
                    is SecurityException -> DbFileError.NoPermission
                    is IOException -> DbFileError.IOError
                    else -> DbFileError("Generic error when working with \$HOME/.kastle/games.db file")
                }
            }
    }
}