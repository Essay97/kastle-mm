import dto.*
import kotlinx.datetime.LocalDate

@DslMarker
annotation class KastleDsl

class GameConfiguration(
    val rooms: List<RoomDto>,
    val player: PlayerDto,
    val initialRoomId: String,
    val items: List<ItemDto>?,
    val characters: List<CharacterDto>?,
    val metadata: MetadataDto?,
    val winningConditions: WinningConditionsDto?,
    val preface: String?
)

@KastleDsl
fun game(initialRoomId: String, init: GameScope.() -> Unit): GameConfiguration {
    val scope = GameScope(initialRoomId)
    scope.init()
    return scope.build()
}

@KastleDsl
class GameScope(private val initialRoomId: String) {
    private var metadata = MetadataDto()
    private var player = PlayerDto("Player", "Default player")
    private var rooms = mutableListOf<RoomDto>()
    private var items = mutableListOf<ItemDto>()
    private var characters = mutableListOf<CharacterDto>()
    private var winningConditions: WinningConditionsDto? = null

    var preface: String? = null


    fun metadata(init: MetadataScope.() -> Unit) {
        val scope = MetadataScope()
        scope.init()
        metadata = scope.build()
    }

    fun player(init: PlayerScope.() -> Unit) {
        val scope = PlayerScope()
        scope.init()
        player = scope.build()
    }

    fun room(roomId: String, init: RoomScope.() -> Unit) {
        val scope = RoomScope(roomId)
        scope.init()
        val result = scope.build()
        items += result.items
        characters += result.characters
        rooms += result.room
    }

    fun build(): GameConfiguration = GameConfiguration(
        initialRoomId = initialRoomId,
        metadata = metadata,
        player = player,
        rooms = rooms,
        items = items,
        characters = characters,
        preface = preface,
        winningConditions = winningConditions
    )
}

@KastleDsl
class MetadataScope {
    var author: String? = null
    var version: String? = null
    var published: LocalDate? = null
    var kastleVersions: List<String>? = null

    fun build(): MetadataDto = MetadataDto(
        author = author,
        version = version,
        published = published,
        kastleVersions = kastleVersions
    )
}

@KastleDsl
class PlayerScope {
    var name = "Player"
    var description: String? = null

    fun build(): PlayerDto = PlayerDto(
        name = name,
        description = description
    )
}

@KastleDsl
class RoomScope(private val roomId: String) {
    var name = roomId
    var description: String? = null

    private var north: DirectionDto? = null
    private var south: DirectionDto? = null
    private var east: DirectionDto? = null
    private var west: DirectionDto? = null
    private val items: MutableList<ItemDto> = mutableListOf()
    private val characters: MutableList<CharacterDto> = mutableListOf()

    fun north(roomId: String, init: DirectionScope.() -> Unit) {
        val scope = DirectionScope(roomId)
        scope.init()
        north = scope.build()
    }

    fun south(roomId: String, init: DirectionScope.() -> Unit) {
        val scope = DirectionScope(roomId)
        scope.init()
        south = scope.build()
    }

    fun west(roomId: String, init: DirectionScope.() -> Unit) {
        val scope = DirectionScope(roomId)
        scope.init()
        west = scope.build()
    }

    fun east(roomId: String, init: DirectionScope.() -> Unit) {
        val scope = DirectionScope(roomId)
        scope.init()
        east = scope.build()
    }

    fun build(): BuildResult = BuildResult(
        room = RoomDto(
            name = name,
            id = roomId,
            items = items.map { it.id },
            characters = characters.map { it.id },
            description = description,
            links = LinksDto(north, south, east, west)
        ),
        characters = characters,
        items = items
    )

    fun character(characterId: String, init: CharacterScope.() -> Unit) {
        TODO()
    }

    fun item(itemId: String, init: ItemScope.() -> Unit) {
        TODO()
    }

    class BuildResult(val room: RoomDto, val items: List<ItemDto>, val characters: List<CharacterDto>)
}

@KastleDsl
class DirectionScope(private val roomId: String) {
    var state = LinkState.OPEN
    var behavior = LinkBehavior.CONSTANT

    private var triggers: List<String> = listOf()

    fun triggers(vararg itemIds: String) {
        triggers = itemIds.asList()
    }

    fun build(): DirectionDto = DirectionDto(
        roomId = roomId,
        state = DirectionStateDto(state, behavior, triggers),
    )
}

class CharacterScope(private val characterId: String) {
    var name = characterId
}

class ItemScope(private val itemId: String) {
    var name = itemId
    var description: String? = null
    private var matchers: List<String> = listOf()

    fun matchers(vararg words: String) {
        matchers = words.asList()
    }
}

fun test() {
    game("my-room") {
        preface = "Once upon a time, not so long ago..."

        metadata {
            author = "Enrico Saggiorato"
            version = "1.0.0"
            published = LocalDate(2024, 5, 4)
            kastleVersions = listOf("1.0")
        }

        player {
            name = "Kyle"
            description = "Now this is a hero"
        }

        room("r-room") {
            name = "Test room"
            description = "This is a test room"
            north("r-second-room") {
                state = LinkState.OPEN
                behavior = LinkBehavior.COMPLETE
                triggers("i-item1", "i-item2")
            }
        }
    }
}