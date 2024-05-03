package service.model.commands

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import service.InteractableManager
import service.model.Characters
import service.model.nextaction.ExecuteDialogue
import service.model.nextaction.NextAction
import error.GameRuntimeError
import error.KastleError

class TalkCommand(private val matcher: String, private val interactableManager: InteractableManager) : GameCommand() {
    override fun execute(): Either<KastleError, NextAction> = either {
        val characterId = interactableManager.getForDialogue(matcher).bind()
        val character = Characters.getById(characterId)!!
        // getDialogue() must bind before checking if the character is idling or CharacterNotTalker error will never be raised
        val dialogue = character.getDialogue().bind()
        ensure(!character.idling) { GameRuntimeError.CharacterAlreadyTalked(character.name) }
        character.idling = true
        ExecuteDialogue(dialogue)
    }
}