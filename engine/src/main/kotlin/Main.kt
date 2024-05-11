import cli.commands.*
import cli.commands.List
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) = Kastle()
    .subcommands(List(), Install(), Uninstall(), Play(), Test())
    .main(args)
