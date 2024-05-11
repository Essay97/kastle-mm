package it.saggioland.kastle.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.varabyte.kotter.foundation.session
import it.saggioland.kastle.cli.components.choiceSelector

class Test : CliktCommand() {
    override fun run() {
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