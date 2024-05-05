package model

object Characters {
    private val characters = mutableMapOf<model.CharacterId, model.Character>()

    fun getById(id: model.CharacterId): model.Character? = model.Characters.characters[id]

    fun add(vararg characters: model.Character) {
        characters.forEach {
            model.Characters.characters[it.id] = it
        }
    }
}