package friarLib2.commands

import edu.wpi.first.wpilibj2.command.*

/**
 * Interface that requires overriding the unary plus operator for
 * edu.wpi.first.wpilibj2.command.Command
 */
interface CommandBuilder {
    operator fun Command.unaryPlus() {}
    operator fun Command.unaryMinus() {}
    fun buildCommand(): Command
}

class SequentialCommandGroupBuilder: SequentialCommandGroup(), CommandBuilder {
    override operator fun Command.unaryPlus() =
        addCommands(this)

    override fun buildCommand(): Command = this
}

class ParallelCommandGroupBuilder: ParallelCommandGroup(), CommandBuilder {
    override operator fun Command.unaryPlus() =
        addCommands(this)

    override fun buildCommand(): Command = this
}

class ParallelRaceGroupBuilder: ParallelRaceGroup(), CommandBuilder {
    override operator fun Command.unaryPlus() =
        addCommands(this)

    override fun buildCommand(): Command = this
}

class ParallelDeadlineGroupBuilder():
    ParallelDeadlineGroup(WaitCommand(0.0)),
    CommandBuilder
{
    override operator fun Command.unaryPlus() =
        addCommands(this)

    /** Use the unary minus operator to add the deadline */
    override operator fun Command.unaryMinus() =
        setDeadline(this)

    override fun buildCommand(): Command = this
}

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