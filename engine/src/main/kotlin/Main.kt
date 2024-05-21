package it.saggioland.kastle

import com.github.ajalt.clikt.core.subcommands
import it.saggioland.kastle.cli.commands.*
import it.saggioland.kastle.cli.commands.List

fun main(args: Array<String>) = Kastle()
    .subcommands(List(), Install(), Uninstall(), Play(), Test(), Info())
    .main(args)
