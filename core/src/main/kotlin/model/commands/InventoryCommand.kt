package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.right
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.model.nextaction.ShowInventory
import it.saggioland.kastle.error.KastleError

class InventoryCommand : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = ShowInventory.right()
}