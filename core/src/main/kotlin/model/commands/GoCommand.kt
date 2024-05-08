package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.raise.either
import it.saggioland.kastle.service.MovementManager
import it.saggioland.kastle.model.Direction
import it.saggioland.kastle.model.nextaction.DescribeCurrentRoom
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

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