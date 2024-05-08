package presentation

import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import it.saggioland.kastle.service.InstallationManager
import java.nio.file.Path

class Install : CliktCommand(help = "Install a new game") {

    private val configManager = InstallationManager().getOrElse { throw ProgramResult(4) }

    private val gameName by argument()
    private val gamePath by argument().path(
        mustBeReadable = true,
        mustExist = true,
    )


    override fun run() {
        if (gameExists(gameName, gamePath)) {
            echo("error: game $gameName already installed.")
            throw ProgramResult(2)
        } else {
            configManager.installGame(gameName, gamePath)
            echo("$gameName installed correctly.")
        }
    }

    private fun gameExists(name: String, path: Path): Boolean = configManager.allGames.entries.any { game ->
        game.key == name || game.value == path
    }

}