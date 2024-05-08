package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.right
import it.saggioland.kastle.service.MovementManager
import it.saggioland.kastle.model.nextaction.DescribeCurrentRoom
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

class WhereCommand(private val movementManager: MovementManager) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = DescribeCurrentRoom.right()
}