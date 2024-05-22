package io.github.essay97.kastle.cli.commands

import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import com.varabyte.kotter.foundation.session
import io.github.essay97.kastle.cli.components.choiceSelector
import io.github.essay97.kastle.service.InstallationManager

class Test : CliktCommand() {
    private val installationManager: InstallationManager = InstallationManager().getOrElse {
        echo(it.description, err = true)
        throw ProgramResult(4)
    }

    private val arg by argument()
    override fun run() {
        installationManager.getGames()
        val choices = listOf("First choice", "Second choice", "Third choice", "Fourth choice")
        session {
            /*var choice by liveVarOf(0)

            section {
                choices.forEachIndexed { i, c ->
                    if (i == choice) {
                        text("[${i + 1}] "); underline { textLine(c) }
                    } else {
                        textLine(" ${i + 1}  $c")
                    }
                }
            }.runUntilKeyPressed(Keys.ENTER) {
                onKeyPressed {
                    if (key == Keys.UP) {
                        choice = (choice - 1 + choices.size) % choices.size
                    }
                    if (key == Keys.DOWN) {
                        choice = (choice + 1) % choices.size
                    }
                }
            }*/
            choiceSelector(choices)
        }
    }
}