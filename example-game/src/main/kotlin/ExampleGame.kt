package io.github.essay97.example

import io.github.essay97.kastle.dto.GameConfiguration
import io.github.essay97.kastle.dto.LinkState
import io.github.essay97.kastle.dsl.game
import io.github.essay97.kastle.model.LinkBehavior
import io.github.essay97.kastle.service.GameProvider

class ExampleGame : GameProvider {
    override fun provideConfiguration(): GameConfiguration = game("r-room-1") {
        metadata {
            author = "Enrico Saggiorato"
        }

        preface = "This is an example game, actually the first one written with the new Kastle DSL"

        room("r-room-1") {
            name = "Initial room"
            description = "This is the first room that the player sees"
            north("r-winner") {
                behavior = LinkBehavior.CONSTANT
                state = LinkState.OPEN
            }

            item("i-table") {
                name = "Table"
                description = "A nice wooden table"
                storable = false
            }

            character("c-doorman") {
                name = "Jack"
                description = "Jack is the official Kastle's doorman!"
                dialogue {
                    firstQuestion("d-first") {
                        text = "Hey player! are you ready for the adventure?"
                        answer {
                            text = "Yes!"
                            nextQuestion = "d-ifyes"
                        }
                        answer {
                            text = "No..."
                            nextQuestion = "d-ifno"
                        }
                    }

                    question("d-ifyes") {
                        text = "Ok, take this and go the the next room!"
                        reward("i-diploma") {
                            name = "Diploma"
                            description = "It's a nice diploma with decorated borders"
                        }
                    }
                }
            }

        }

        room("r-winner") {
            name = "Room of Victory"
            description = "By entering this room the game ends"
        }

        winIf {
            playerEnters = "r-winner"
        }
    }
}