package friarLib2.commands.builders

import edu.wpi.first.wpilibj2.command.Command
import friarLib2.commands.TimedCommand
import java.util.Collections.emptyList

class TimedCommandBuilder(
    var startTime: Double = 0.0,
    var runTime: Double = 0.0,
    private val commands: MutableList<Command> = emptyList()
): CommandBuilder {
    var endTime: Double
        get() = startTime + runTime
        set(value) {
            runTime = value - startTime
        }

    override operator fun Command.unaryPlus() {
        commands.add(this)
    }

    override fun buildCommand(): Command {
        val command = group {
            // Only create a parallel command group if we have more than one command to run
            if (commands.size == 1) {
                +commands[0]
            } else {
                +parallel { for (c in commands) { +c } }
            }
        }

        return TimedCommand(startTime, runTime, command)
    }
}