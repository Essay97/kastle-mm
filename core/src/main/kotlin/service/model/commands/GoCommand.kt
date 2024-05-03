package service.model.commands

import arrow.core.Either
import arrow.core.raise.either
import service.MovementManager
import service.model.Direction
import service.model.nextaction.DescribeCurrentRoom
import service.model.nextaction.NextAction
import error.KastleError

class GoCommand(private val direction: Direction, private val movementManager: MovementManager) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = either {
        (when (direction) {
            Direction.NORTH -> movementManager.moveNorth()
            Direction.SOUTH -> movementManager.moveSouth()
            Direction.WEST -> movementManager.moveWest()
            Direction.EAST -> movementManager.moveEast()
        }).bind()

        DescribeCurrentRoom
    }

}