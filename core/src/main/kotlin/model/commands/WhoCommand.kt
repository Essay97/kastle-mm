package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.right
import it.saggioland.kastle.model.nextaction.DescribePlayer
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

class WhoCommand : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = DescribePlayer.right()

}