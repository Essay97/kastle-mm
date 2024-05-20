package it.saggioland.kastle.cli.commands

import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import it.saggioland.kastle.service.InstallationManager
import java.nio.file.Path
import kotlin.io.path.nameWithoutExtension

class Install : CliktCommand(help = "Install a new game") {

    private val installationManager = InstallationManager().getOrElse { throw ProgramResult(4) }

    private val gameClass by argument()
    private val gamePath by argument().path(
        mustBeReadable = true,
        mustExist = true,
        canBeDir = false
    )

    private val gameName by option("-n", "--name").default(gamePath.nameWithoutExtension)


    override fun run() {
        if (gameExists(gameName, gamePath)) {
            echo("error: game $gameName already installed.")
            throw ProgramResult(2)
        } else {
            installationManager.installGame(gameName, gamePath)
            echo("$gameName installed correctly.")
        }
    }

    private fun gameExists(name: String, path: Path): Boolean = installationManager.allGames.entries.any { game ->
        game.key == name || game.value == path
    }

}