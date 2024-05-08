package it.saggioland.example

import it.saggioland.kastle.dto.GameConfiguration
import it.saggioland.kastle.dto.LinkState
import it.saggioland.dsl.game
import model.LinkBehavior
import service.GameProvider

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