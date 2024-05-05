package model.nextaction

import model.Dialogue
import model.Room
import model.StorableItem
import model.capabilities.Inspectable

sealed class NextAction
data object DescribeCurrentRoom : NextAction()
data class DescribeInspectable(val inspectable: Inspectable) : NextAction()
data object EndGame : NextAction()
data object ShowInventory : NextAction()
data class ConfirmGrab(val item: StorableItem) : NextAction()
data class ConfirmDrop(val item: StorableItem) : NextAction()
data object DescribePlayer : NextAction()
data class ConfirmOpen(val source: Room, val destination: Room) : NextAction()
data class ConfirmClose(val source: Room, val destination: Room) : NextAction()
data class ExecuteDialogue(val dialogue: Dialogue) : NextAction()


