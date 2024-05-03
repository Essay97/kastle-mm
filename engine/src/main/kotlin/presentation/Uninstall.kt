package presentation

import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import service.ConfigManager

class Uninstall : CliktCommand(help = "Uninstall an installed game") {

    private val gameName by argument()

    private val configManager = ConfigManager().getOrElse { throw ProgramResult(4) }

    override fun run() {
        if (!configManager.allGames.containsKey(gameName)) {
            echo("error: game $gameName does not exist.", err = true)
            throw ProgramResult(3)
        }
        configManager.uninstallGame(gameName)
        echo("$gameName uninstalled correctly.")
    }
}