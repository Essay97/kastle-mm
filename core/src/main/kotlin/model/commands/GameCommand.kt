package it.saggioland.kastle.model.commands

import arrow.core.Either
import it.saggioland.kastle.service.*
import it.saggioland.kastle.model.Direction
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.KastleError

abstract class GameCommand {
    abstract fun execute(): Either<KastleError, NextAction>
}

class CommandFactory(
    private val movementManager: MovementManager,
    private val runManager: RunManager,
    private val interactableManager: InteractableManager,
    private val inventoryManager: InventoryManager,
    private val state: GameState
) {
    fun createGoCommand(direction: Direction): GoCommand = GoCommand(direction, movementManager)
    fun createEndCommand(): EndCommand = EndCommand(runManager)
    fun createInspectCommand(matcher: String): InspectCommand =
        InspectCommand(matcher, interactableManager)
    fun createInventoryCommand(): InventoryCommand = InventoryCommand()
    fun createGrabCommand(matcher: String): GrabCommand = GrabCommand(matcher, inventoryManager, interactableManager)
    fun createDropCommand(matcher: String): DropCommand = DropCommand(matcher, inventoryManager, state)
    fun createWhereCommand(): WhereCommand = WhereCommand(movementManager)
    fun createWhoCommand(): WhoCommand = WhoCommand()
    fun createOpenCommand(direction: Direction): OpenCommand = OpenCommand(direction, movementManager, state)
    fun createCloseCommand(direction: Direction): CloseCommand = CloseCommand(direction, movementManager, state)
    fun createTalkCommand(matcher: String): TalkCommand = TalkCommand(matcher, interactableManager)
}

