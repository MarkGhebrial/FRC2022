package friarLib2.commands.builders

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.*

/**
 * A command that starts after a certain amount of time and ends after
 * some amount of time
 */
class TimedCommand(
    startTime: Double,
    runTime: Double,
    command: Command
): SequentialCommandGroup(
    WaitCommand(startTime),
    command.withTimeout(runTime)
)

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

open class AutonomousBuilder: CommandGroupBuilder() {

    /**
     * A timer that starts counting when this command initializes
     */
    protected val timer: Timer = Timer()

    init {
        timer.stop()
        timer.reset()
    }

    /**
     * Use a TimedCommandBuilder to create a new TimedCommand
     *
     * <p>Use the unary plus operator to add a command to the parallel
     * command group
     */
    fun timed(init: TimedCommandBuilder.() -> Unit): Command =
        initCommand(TimedCommandBuilder(), init)

    override fun initialize() {
        super.initialize()

        timer.reset()
        timer.start()
    }
}