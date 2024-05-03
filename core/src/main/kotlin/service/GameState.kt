package service

import service.model.ItemId
import service.model.RoomId

data class GameState(var currentRoom: RoomId) {
    val inventory =  mutableListOf<ItemId>()
}