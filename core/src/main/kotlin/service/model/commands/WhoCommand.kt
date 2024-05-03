package service.model.commands

import arrow.core.Either
import arrow.core.right
import service.model.nextaction.DescribePlayer
import service.model.nextaction.NextAction
import error.KastleError

class WhoCommand : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = DescribePlayer.right()

}