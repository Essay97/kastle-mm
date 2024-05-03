package service.model.commands

import arrow.core.Either
import arrow.core.right
import service.model.nextaction.NextAction
import service.model.nextaction.ShowInventory
import error.KastleError

class InventoryCommand : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = ShowInventory.right()
}