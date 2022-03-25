package friarLib2.commands.builders

import edu.wpi.first.wpilibj2.command.Command
import friarLib2.commands.TimedCommand

class TimedCommandBuilder(
    var startTime: Double = 0.0,
    var runTime: Double = 0.0,
    private val commandBuilder: ParallelCommandGroupBuilder = ParallelCommandGroupBuilder()
): CommandBuilder {
    var endTime: Double
        get() = startTime + runTime
        set(value) {
            runTime = value - startTime
        }

    override operator fun Command.unaryPlus() {
        commandBuilder.addCommands(this)
    }

    override fun buildCommand(): Command =
        TimedCommand(startTime, runTime, commandBuilder.buildCommand())
}