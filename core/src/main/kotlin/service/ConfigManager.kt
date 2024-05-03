package service

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import error.*
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.io.path.absolutePathString

class ConfigManager private constructor(private val gamesDbFile: File) {

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
            Paths.get(gamesDbFile.toURI()),
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

    fun getGameFile(name: String): Either<ConfigError, File> = either {
        val file = allGames[name]?.toFile()
        ensureNotNull(file) { GameFileError.NonExistentGame }
    }

    companion object {
        operator fun invoke(): Either<ConfigError, ConfigManager> = either {
            val home = getUserHome().bind()
            val kastleDir = handleKastleDirectory(home).bind()
            val gamesDbFile = handleGameDbFile(kastleDir).bind()
            ConfigManager(gamesDbFile)
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

        private fun handleKastleDirectory(home: String): Either<KastleDirectoryError, File> =
            Either.catch {
                val kastleFolder = File("$home/.kastle")
                if (!kastleFolder.exists() && !kastleFolder.mkdir()) {
                    return KastleDirectoryError.CreationError.left()
                } else {
                    kastleFolder
                }
            }.mapLeft {
                when (it) {
                    is SecurityException -> KastleDirectoryError.NoPermission
                    else -> KastleDirectoryError("Generic error when working with \$HOME/.kastle directory")
                }
            }

        private fun handleGameDbFile(kastleDir: File): Either<DbFileError, File> =
            Either.catch {
                val gamesDbFile = File(kastleDir, "games.db")
                if (!gamesDbFile.exists() && !gamesDbFile.createNewFile()) {
                    return DbFileError.CreationError.left()
                } else {
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