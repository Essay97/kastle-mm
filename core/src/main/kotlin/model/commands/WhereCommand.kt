package model.commands

import arrow.core.Either
import arrow.core.right
import service.MovementManager
import model.nextaction.DescribeCurrentRoom
import model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

class WhereCommand(private val movementManager: MovementManager) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = DescribeCurrentRoom.right()
}