package model.commands

import arrow.core.Either
import arrow.core.right
import model.nextaction.DescribePlayer
import model.nextaction.NextAction
import error.KastleError

class WhoCommand : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = DescribePlayer.right()

}