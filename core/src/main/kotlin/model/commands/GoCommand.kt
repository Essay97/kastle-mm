package model.commands

import arrow.core.Either
import arrow.core.raise.either
import service.MovementManager
import model.Direction
import model.nextaction.DescribeCurrentRoom
import model.nextaction.NextAction
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