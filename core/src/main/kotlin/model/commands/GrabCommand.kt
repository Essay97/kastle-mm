package model.commands

import arrow.core.Either
import arrow.core.raise.either
import service.InteractableManager
import service.InventoryManager
import model.Items
import model.nextaction.ConfirmGrab
import model.nextaction.NextAction
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