package it.saggioland.kastle

import com.github.ajalt.clikt.core.subcommands
import it.saggioland.kastle.cli.commands.*

fun main(args: Array<String>) = Kastle()
    .subcommands(List(), Install(),/* Uninstall(), Play(), Info(),*/ Test())
    .main(args)
