package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.right
import it.saggioland.kastle.service.RunManager
import it.saggioland.kastle.model.nextaction.EndGame
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

class EndCommand(private val runManager: RunManager) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> {
        runManager.isRunning = false
        return EndGame.right()
    }

}