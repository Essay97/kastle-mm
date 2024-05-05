package service

import arrow.core.Either
import arrow.core.raise.either
import error.KastleError
import java.io.File

class ConfigurationManager {
    fun getManagersForGameFile(file: File): Either<KastleError, Managers> = either {
        Managers(
            state = GameState()
        )
    }
}

class Managers(
    private val commandManager: CommandManager,
    private val interactableManager: InteractableManager,
    private val movementManager: MovementManager,
    private val informationManager: InformationManager,
    private val runManager: RunManager,
    private val inventoryManager: InventoryManager,
    private val state: GameState
) {
    operator fun component1(): CommandManager = commandManager
    operator fun component2(): InteractableManager = interactableManager
    operator fun component3(): MovementManager = movementManager
    operator fun component4(): InformationManager = informationManager
    operator fun component5(): RunManager = runManager
    operator fun component6(): InventoryManager = inventoryManager
    operator fun component7(): GameState = state
}