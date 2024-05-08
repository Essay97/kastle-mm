package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.raise.either
import it.saggioland.kastle.service.InteractableManager
import it.saggioland.kastle.service.InventoryManager
import it.saggioland.kastle.model.Items
import it.saggioland.kastle.model.nextaction.ConfirmGrab
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

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