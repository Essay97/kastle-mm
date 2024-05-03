package service.model.commands

import arrow.core.Either
import arrow.core.right
import service.MovementManager
import service.model.nextaction.DescribeCurrentRoom
import service.model.nextaction.NextAction
import error.KastleError

class WhereCommand(private val movementManager: MovementManager) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = DescribeCurrentRoom.right()
}