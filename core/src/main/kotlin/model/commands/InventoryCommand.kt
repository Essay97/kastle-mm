package model.commands

import arrow.core.Either
import arrow.core.right
import model.nextaction.NextAction
import model.nextaction.ShowInventory
import it.saggioland.kastle.error.KastleError

class InventoryCommand : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = ShowInventory.right()
}