package io.github.essay97.kastle.cli.ingamecommands

import com.varabyte.kotter.foundation.input.Completions

val gameCommands = listOf("go", "inspect", "grab", "drop", "end", "inventory", "where", "who", "open", "close", "talk")
val gameCommandCompletions = Completions(*gameCommands.map { "$it " }.toTypedArray())