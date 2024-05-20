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

    private val installationManager = InstallationManager().getOrElse {
        echo(it.description, err = true)
        throw ProgramResult(4)
    }

    private val gameClass by argument()
    private val gamePath by argument().path(
        mustBeReadable = true,
        mustExist = true,
        canBeDir = false
    )

    private val gameName by option("-n", "--name").default(gamePath.nameWithoutExtension)


    override fun run() {
        installationManager.installGame(gameName, gamePath, gameClass).getOrElse {
            echo(it.description, err = true)
            throw ProgramResult(2)
        }
        echo("$gameName installed correctly.")
    }
}