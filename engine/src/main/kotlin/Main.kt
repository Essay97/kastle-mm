import com.github.ajalt.clikt.core.subcommands
import presentation.*
import presentation.List

fun main(args: Array<String>) = Kastle()
    .subcommands(List(), Install(), Uninstall(), Play(), Test())
    .main(args)
