package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.raise.either
import it.saggioland.kastle.service.GameState
import it.saggioland.kastle.service.MovementManager
import it.saggioland.kastle.model.Direction
import it.saggioland.kastle.model.Rooms
import it.saggioland.kastle.model.nextaction.ConfirmClose
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

class CloseCommand(
    private val direction: Direction, private val movementManager: MovementManager, val state: GameState
) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = either {
        val destination = (when (direction) {
            Direction.NORTH -> movementManager.closeNorth()
            Direction.SOUTH -> movementManager.closeSouth()
            Direction.WEST -> movementManager.closeWest()
            Direction.EAST -> movementManager.closeEast()
        }).bind()
        ConfirmClose(Rooms.getById(state.currentRoom)!!, destination)
    }
}