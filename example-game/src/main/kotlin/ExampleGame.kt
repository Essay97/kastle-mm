package io.github.essay97.example

import io.github.essay97.kastle.dsl.game
import io.github.essay97.kastle.dto.GameConfiguration
import io.github.essay97.kastle.service.GameProvider
import kotlinx.datetime.LocalDate

class ExampleGame : GameProvider {
    override fun provideConfiguration(): GameConfiguration = game("r-room-1") {
        metadata {
            author = "Enrico Saggiorato"
            name = "Tutorial Game"
            version = "1.0.0"
            kastleVersions = listOf("1.0.0", "1.0.1")
            published = LocalDate(2024, 5, 24)
        }

        player {
            name = "Enrico"
            description = "Enrico is the author of Kastle and also an awesome hero"
        }

        preface = "This is an example game, actually the first one written with the new Kastle DSL"

        room("r-room-1") {
            name = "Initial room"
            description = "This is the first room that the player sees"
            north("r-winner")

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
                            description = """
                                It's a nice diploma with decorated borders. It says:
                                'Well done, player! 
                                
                                You reached the end of your first Kastle game. 
                                Move to the north to win the game!'
                            """.trimIndent()
                        }
                    }

                    question("d-ifno") {
                        text = "No problem, I'll wait until you're ready, but you're missing a lot of fun!"
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