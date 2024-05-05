package model.commands

import arrow.core.Either
import arrow.core.raise.either
import service.InteractableManager
import model.nextaction.DescribeInspectable
import model.nextaction.NextAction
import error.KastleError

class InspectCommand(
    private val matcher: String,
    private val interactableManager: InteractableManager
) : GameCommand() {

    override fun execute(): Either<KastleError, NextAction> = either {
        DescribeInspectable(interactableManager.getForInspection(matcher).bind())
    }
}