package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.raise.either
import it.saggioland.kastle.service.InteractableManager
import it.saggioland.kastle.model.nextaction.DescribeInspectable
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

class InspectCommand(
    private val matcher: String,
    private val interactableManager: InteractableManager
) : GameCommand() {

    override fun execute(): Either<KastleError, NextAction> = either {
        DescribeInspectable(interactableManager.getForInspection(matcher).bind())
    }
}