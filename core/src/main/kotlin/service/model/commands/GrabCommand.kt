package service.model.commands

import arrow.core.Either
import arrow.core.raise.either
import service.InteractableManager
import service.InventoryManager
import service.model.Items
import service.model.nextaction.ConfirmGrab
import service.model.nextaction.NextAction
import error.KastleError

class GrabCommand(
    private val matcher: String,
    private val inventoryManager: InventoryManager,
    private val interactableManager: InteractableManager
) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = either {
        val itemId = interactableManager.getForGrab(matcher).bind()
        inventoryManager.addItem(itemId).bind()
        ConfirmGrab(Items.getStorableById(itemId)!!)
    }
}