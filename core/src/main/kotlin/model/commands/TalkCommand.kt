package it.saggioland.kastle.model.commands

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import it.saggioland.kastle.service.InteractableManager
import it.saggioland.kastle.model.nextaction.ExecuteDialogue
import it.saggioland.kastle.model.nextaction.NextAction
import it.saggioland.kastle.error.GameRuntimeError
import it.saggioland.kastle.error.KastleError
import it.saggioland.kastle.model.Characters

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