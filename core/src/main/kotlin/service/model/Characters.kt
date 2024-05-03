package service.model

object Characters {
    private val characters = mutableMapOf<CharacterId, Character>()

    fun getById(id: CharacterId): Character? = characters[id]

    fun add(vararg characters: Character) {
        characters.forEach {
            Characters.characters[it.id] = it
        }
    }
}