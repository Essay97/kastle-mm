package io.github.essay97.kastle.cli.commands

import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import io.github.essay97.kastle.service.InstallationManager

class List : CliktCommand(help = "List all installed games with their file definition location") {

    private val installationManager = InstallationManager().getOrElse {
        echo(it.description, err = true)
        throw ProgramResult(4)
    }

    override fun run() {
        installationManager.getGames().forEach {
            echo(it.gameName)
        }
    }
}