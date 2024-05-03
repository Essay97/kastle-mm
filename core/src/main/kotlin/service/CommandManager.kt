package service

import service.model.commands.GameCommand
import service.model.nextaction.DescribeCurrentRoom
import service.model.nextaction.NextAction
import error.KastleError

class CommandManager {
    private val _history: MutableList<GameCommand> = mutableListOf()
    val history: List<GameCommand>
        get() = _history
    var error: KastleError? = null // If a command raises an error it gets posted here
        private set
    var nextAction: NextAction? = DescribeCurrentRoom
        private set

    fun submit(command: GameCommand) {
        reset()
        _history.add(command)
        command.execute()
            .onLeft {
                error = it
            }
            .onRight {
                nextAction = it
            }
    }

    private fun reset() {
        error = null
        nextAction = null
    }

    fun resetAction() {
        nextAction = null
    }
}