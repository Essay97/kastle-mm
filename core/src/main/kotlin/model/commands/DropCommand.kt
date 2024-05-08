package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import it.saggioland.kastle.service.GameState
import it.saggioland.kastle.service.InventoryManager
import it.saggioland.kastle.model.Items
import it.saggioland.kastle.model.nextaction.ConfirmDrop
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.GameRuntimeError
import it.saggioland.kastle.error.KastleError

class DropCommand(
    private val matcher: String,
    private val inventoryManager: InventoryManager,
    private val state: GameState
) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = either {
        val itemId = state.inventory.find { matcher in Items.getById(it)!!.matchers }
        ensureNotNull(itemId) { GameRuntimeError.ItemNotInInventory(matcher) }
        inventoryManager.removeItem(itemId)
        ConfirmDrop(Items.getStorableById(itemId)!!)
    }
}