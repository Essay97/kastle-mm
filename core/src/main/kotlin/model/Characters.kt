package it.saggioland.kastle.model

object Characters {
    private val characters = mutableMapOf<it.saggioland.kastle.model.CharacterId, it.saggioland.kastle.model.Character>()

    fun getById(id: it.saggioland.kastle.model.CharacterId): it.saggioland.kastle.model.Character? = it.saggioland.kastle.model.Characters.characters[id]

    fun add(vararg characters: it.saggioland.kastle.model.Character) {
        characters.forEach {
            it.saggioland.kastle.model.Characters.characters[it.id] = it
        }
    }
}