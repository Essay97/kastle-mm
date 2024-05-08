package service

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import model.*
import model.capabilities.Inspectable
import it.saggioland.kastle.error.GameRuntimeError

class InteractableManager(private val state: GameState) {
    fun getForInspection(matcher: String): Either<GameRuntimeError.CannotFindInspectable, Inspectable> = either {
        val room = Rooms.getById(state.currentRoom)!!
        if (matcher == "room") {
            room
        } else {
            val inspectables =
                (room.items + state.inventory).map { Items.getById(it)!! } + listOf(room) +
                        room.characters.map { model.Characters.getById(it)!! }

            val id = inspectables.find { matcher in it.matchers }
            ensureNotNull(id) { GameRuntimeError.CannotFindInspectable(matcher) }
        }
    }

    fun getForGrab(matcher: String): Either<GameRuntimeError.CannotFindStorable, ItemId> = either {
        val room = Rooms.getById(state.currentRoom)!!
        val storable = room.items.find {
            val item = Items.getById(it)!!
            item is StorableItem && matcher in item.matchers
        }
        ensureNotNull(storable) { GameRuntimeError.CannotFindStorable(matcher) }
    }

    fun getForDialogue(matcher: String): Either<GameRuntimeError.CannotFindTalker, CharacterId> = either {
        val room = Rooms.getById(state.currentRoom)!!
        val character = room.characters.find { matcher in model.Characters.getById(it)!!.matchers }
        ensureNotNull(character) { GameRuntimeError.CannotFindTalker(matcher) }
    }
}