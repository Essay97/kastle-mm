package io.github.essay97.kastle

import com.github.ajalt.clikt.core.subcommands
import io.github.essay97.kastle.cli.commands.*

fun main(args: Array<String>) = Kastle()
    .subcommands(List(), Install(),/* Uninstall(), Play(), Info(),*/ Test())
    .main(args)
