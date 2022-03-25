package friarLib2.commands.builders

import edu.wpi.first.wpilibj2.command.*

open class CommandGroupBuilder(): CommandBase() {
    // The command group that contains all other commands
    private var rootCommand: Command? = null;

    protected fun <B: CommandBuilder> initCommand(commandBuilder: B, init: B.() -> Unit): Command {
        commandBuilder.init()

        // Get the built command from the command builder
        val built = commandBuilder.buildCommand();

        // Make this command the root command
        rootCommand = built

        return built
    }

    // Add a SequentialCommandGroup
    fun sequential(init: SequentialCommandGroupBuilder.() -> Unit): Command =
        initCommand(SequentialCommandGroupBuilder(), init)

    // Add a ParallelCommandGroup
    fun parallel(init: ParallelCommandGroupBuilder.() -> Unit): Command =
        initCommand(ParallelCommandGroupBuilder(), init)

    // Add a ParallelRaceGroup
    fun parallelRace(init: ParallelRaceGroup.() -> Unit): Command =
        initCommand(ParallelRaceGroupBuilder(), init)

    fun parallelDeadline(init: ParallelDeadlineGroup.() -> Unit): Command =
        initCommand(ParallelDeadlineGroupBuilder(), init)

    override fun initialize() {
        rootCommand?.initialize() ?: println("No commands have been added to the command group builder")
    }

    override fun execute() {
        rootCommand?.execute() ?: println("No commands have been added to the command group builder")
    }

    override fun end(interrupted: Boolean) {
        rootCommand?.end(interrupted) ?: println("No commands have been added to the command group builder")
    }

    override fun isFinished(): Boolean {
        return rootCommand?.isFinished() ?: true // Finish immediately if there's no command to run
    }
}