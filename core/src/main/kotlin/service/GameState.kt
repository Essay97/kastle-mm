package service

import model.ItemId
import model.RoomId

data class GameState(var currentRoom: RoomId) {
    val inventory =  mutableListOf<ItemId>()
}