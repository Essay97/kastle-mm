package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.raise.either
import it.saggioland.kastle.service.GameState
import it.saggioland.kastle.service.MovementManager
import it.saggioland.kastle.model.Direction
import it.saggioland.kastle.model.Rooms
import it.saggioland.kastle.model.nextaction.ConfirmOpen
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

class OpenCommand(private val direction: Direction, private val movementManager: MovementManager, private val state: GameState) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = either {
        val destination = (when (direction) {
            Direction.NORTH -> movementManager.openNorth()
            Direction.SOUTH -> movementManager.openSouth()
            Direction.WEST -> movementManager.openWest()
            Direction.EAST -> movementManager.openEast()
        }).bind()
        ConfirmOpen(Rooms.getById(state.currentRoom)!!, destination)
    }
}