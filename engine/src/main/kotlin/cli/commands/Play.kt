package it.saggioland.kastle.cli.commands

import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import com.varabyte.kotter.foundation.session
import com.varabyte.kotter.foundation.text.bold
import com.varabyte.kotter.foundation.text.cyan
import com.varabyte.kotter.foundation.text.text
import com.varabyte.kotter.foundation.text.textLine
import it.saggioland.kastle.cli.ingamecommands.getCommand
import it.saggioland.kastle.cli.components.*
import it.saggioland.kastle.service.InstallationManager
import it.saggioland.kastle.service.ConfigurationManager
import it.saggioland.kastle.model.commands.CommandFactory

class Play : CliktCommand(help = "Start playing with the specified game") {

    private val installationManager = InstallationManager().getOrElse { throw ProgramResult(4) }
    private val configurationManager = ConfigurationManager()

    private val gameName by argument()

    override fun run() {
        /*
         * Setup dependencies
         */
        val gameFile = /*installationManager.getGameClass(gameName).getOrElse {
            echo(it.description, err = true)
            throw ProgramResult(3)
        }*/ "it.saggioland.example.ExampleGame"
        val (commands, interactables, movement, information, running, inventory, state) =
            configurationManager.getManagersForGameClass(gameFile).getOrElse {
                echo(it.description, err = true)
                throw ProgramResult(3)
            }

        val factory = CommandFactory(movement, running, interactables, inventory, state)

        /*
         * Start rendering the game
         */
        session {
            header(information.metadata, gameName)

            section {
                // Usually the preface carries its own newline at the end
                text(information.preface)
            }.run()

            do {
                if (!running.win) {
                    handleNextAction(commands, information, state)
                    nextTurn().fold(
                        ifLeft = { showError(it.description) },
                        ifRight = {
                            getCommand(it.first, it.second, factory)
                                .onLeft { error ->
                                    showError(error.description)
                                }
                                .onRight { command ->
                                    commands.submit(command)
                                    if (commands.error != null) {
                                        showWarning(commands.error!!.description)
                                    }
                                }
                        }
                    )
                } else {
                    section {
                        cyan { bold { textLine("You won!") } }
                    }.run()
                    commands.submit(factory.createEndCommand())
                }
            } while (running.isRunning)


            // TODO add saving game logic here
            section {
                textLine("Closing the game, see you on the next adventure!")
            }.run()
        }
    }
}