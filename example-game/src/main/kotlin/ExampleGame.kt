package io.github.essay97.example

import io.github.essay97.kastle.dto.GameConfiguration
import io.github.essay97.kastle.dto.LinkState
import io.github.essay97.dsl.game
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