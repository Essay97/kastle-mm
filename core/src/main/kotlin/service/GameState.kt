package it.saggioland.kastle.service

import it.saggioland.kastle.model.ItemId
import it.saggioland.kastle.model.RoomId

data class GameState(var currentRoom: RoomId) {
    val inventory =  mutableListOf<ItemId>()
}