package service.model.commands

import arrow.core.Either
import arrow.core.raise.either
import service.InteractableManager
import service.model.nextaction.DescribeInspectable
import service.model.nextaction.NextAction
import error.KastleError

class InspectCommand(
    private val matcher: String,
    private val interactableManager: InteractableManager
) : GameCommand() {

    override fun execute(): Either<KastleError, NextAction> = either {
        DescribeInspectable(interactableManager.getForInspection(matcher).bind())
    }
}