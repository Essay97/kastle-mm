package service.model.commands

import arrow.core.Either
import arrow.core.raise.either
import service.GameState
import service.MovementManager
import service.model.Direction
import service.model.Rooms
import service.model.nextaction.ConfirmClose
import service.model.nextaction.NextAction
import error.KastleError

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