package io.github.essay97.kastle.cli.commands

import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.essay97.kastle.service.InstallationManager

class Info : CliktCommand(help = "Get information about a specific game") {

    private val game by argument()

    private val installationManager = InstallationManager().getOrElse {
        echo(it.description, err = true)
        throw ProgramResult(4)
    }

    override fun run() {
        val gameInfo = installationManager.getByGameName(game).getOrElse {
            echo(it.description, err = true)
            throw ProgramResult(6)
        }
        echo(gameInfo.gameName)
        echo("Game file: ${gameInfo.fileName}")
        echo("Main class: ${gameInfo.mainClass}")
    }
}