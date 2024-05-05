package model.commands

import arrow.core.Either
import arrow.core.right
import service.RunManager
import model.nextaction.EndGame
import model.nextaction.NextAction
import error.KastleError

class EndCommand(private val runManager: RunManager) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> {
        runManager.isRunning = false
        return EndGame.right()
    }

}