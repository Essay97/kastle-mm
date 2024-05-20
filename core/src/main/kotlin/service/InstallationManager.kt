package it.saggioland.kastle.service

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import it.saggioland.kastle.db.Database
import it.saggioland.kastle.db.GamesQueries
import it.saggioland.kastle.error.*
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.*

class InstallationManager private constructor(private val gamesDbFile: Path) {

    private val queries: GamesQueries

    init {
        val jdbcString = "jdbc:sqlite:${gamesDbFile.absolutePathString()}"
        val driver: SqlDriver = JdbcSqliteDriver(url = jdbcString, schema = Database.Schema)

        queries = Database(driver).gamesQueries
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

    fun installGame(name: String, path: Path, className: String): Either<ConfigError, Unit> = either {
        // Insert game into database
        val game = queries.getFilteredGames(name, className, path.pathString).executeAsOneOrNull()
        ensureNotNull(game) { GameFileError.GameAlreadyExists }
        queries.insert(gameName = name, mainClass = className, fileName = path.pathString)

        // Move game file into games folder. Needed for ServiceLoader so that all files are in a predictable folder
        val gamesFolder = handleGamesFolder()
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
        queries.getByGameName(name).executeAsOne().mainClass
    }

    companion object {
        operator fun invoke(): Either<ConfigError, InstallationManager> = either {
            val gamesDbFile = handleGameDbFile().bind()
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

        private fun handleKastleDirectory(): Either<ConfigError, Path> {
            val home = getUserHome().getOrElse { return it.left() }
            return Either.catch {
                val kastleFolder = Paths.get(home).resolve(".kastle")
                if (!Files.exists(kastleFolder)) {
                    Files.createDirectory(kastleFolder)
                }
                kastleFolder
            }.mapLeft {
                when (it) {
                    is SecurityException -> KastleDirectoryError.NoPermission
                    else -> KastleDirectoryError("Generic error when working with \$HOME/.kastle directory")
                }
            }
        }


        private fun handleGameDbFile(): Either<ConfigError, Path> {
            val kastleDir = handleKastleDirectory().getOrElse { return it.left() }
            return Either.catch {
                val gamesDbFile = kastleDir.resolve("games.db")
                if (!Files.exists(gamesDbFile)) {
                    Files.createFile(gamesDbFile)
                }
                gamesDbFile
            }.mapLeft {
                when (it) {
                    is SecurityException -> DbFileError.NoPermission
                    is IOException -> DbFileError.IOError
                    else -> DbFileError("Generic error when working with \$HOME/.kastle/games.db file")
                }
            }
        }


        private fun handleGamesFolder(): Either<ConfigError, Path> {
            val kastleDir = handleKastleDirectory().getOrElse { return it.left() }
            return Either.catch {
                val gamesFolder = kastleDir.resolve("games")
                if (!Files.exists(gamesFolder)) {
                    Files.createDirectory(gamesFolder)
                }
                gamesFolder
            }.mapLeft {
                when (it) {
                    is SecurityException -> GamesDirectoryError.NoPermission
                    else -> GamesDirectoryError("Generic error when working with \$HOME/.kastle/games directory")
                }
            }
        }

    }
}