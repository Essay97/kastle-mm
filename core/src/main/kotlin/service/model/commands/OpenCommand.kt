package service.model.commands

import arrow.core.Either
import arrow.core.raise.either
import service.GameState
import service.MovementManager
import service.model.Direction
import service.model.Rooms
import service.model.nextaction.ConfirmOpen
import service.model.nextaction.NextAction
import error.KastleError

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