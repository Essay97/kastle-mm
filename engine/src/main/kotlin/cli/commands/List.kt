package it.saggioland.kastle.cli.commands

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import it.saggioland.kastle.service.InstallationManager
import it.saggioland.kastle.error.NegativePaddingError

class List : CliktCommand(help = "List all installed games with their file definition location") {

    private val configManager = InstallationManager().getOrElse { throw ProgramResult(4) }

    private val gameNameHeaderPadding = 12
    private val gameNameHeaderString =
        "${" ".repeat(gameNameHeaderPadding)}Game Name${" ".repeat(gameNameHeaderPadding)}"
    private val defaultGameNamePadding = 1

    override fun run() {
        echo(
            """
            |$gameNameHeaderString|                          Path
            |${"-".repeat(gameNameHeaderString.length)}|--------------------------------------------------------
        """.trimIndent()
        )
        configManager.allGames.forEach { entry ->
            val gameNameString = getGameNamePadding(entry.key).fold(
                ifLeft = {
                    // This means that the name is too long and must be ellipsized
                    "${entry.key.take(gameNameHeaderString.length - 3 - defaultGameNamePadding)}..."
                },
                ifRight = { padding ->
                    "${entry.key}${" ".repeat(padding)}"
                }
            )
            echo("|${" ".repeat(defaultGameNamePadding)}$gameNameString| ${entry.value}")
        }
    }

    /**
     * Calculates spaces needed to rendere a game name.
     *
     * Do not confuse it with the [gameNameHeaderPadding] property, that does the same thing for the header of the table.
     * @param[gameName] the name for which to calculate the padding
     */
    private fun getGameNamePadding(gameName: String): Either<NegativePaddingError, Int> = either {
        val padding = gameNameHeaderString.length - gameName.length - defaultGameNamePadding
        ensure(padding >= 0) { NegativePaddingError }
        padding
    }
}