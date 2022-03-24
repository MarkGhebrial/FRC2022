package friarLib2.commands

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.CommandGroupBase
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand

/**
 * Interface that requires overriding the unary plus operator for
 * edu.wpi.first.wpilibj2.command.Command
 */
interface AddCommand {
    operator fun Command.unaryPlus()
}

/**
 * Interface that requires overriding the unary minus operator for
 * edu.wpi.first.wpilibj2.command.Command
 */
interface SubtractCommand {
    operator fun Command.unaryMinus()
}

class SequentialGroup: SequentialCommandGroup(), AddCommand {
    override operator fun Command.unaryPlus() =
        addCommands(this)
}

class ParallelGroup: ParallelCommandGroup(), AddCommand {
    override operator fun Command.unaryPlus() =
        addCommands(this)
}

class ParallelRaceGroup: edu.wpi.first.wpilibj2.command.ParallelRaceGroup(), AddCommand {
    override operator fun Command.unaryPlus() =
        addCommands(this)
}

class ParallelDeadlineGroup():
    edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup(WaitCommand(0.0)),
    AddCommand, SubtractCommand
{
    override operator fun Command.unaryPlus() =
        addCommands(this)

    /** Use the unary minus operator to add the deadline */
    override operator fun Command.unaryMinus() =
        setDeadline(this)
}

open class CommandGroupBuilder(): CommandBase() {
    // The command group that contains all other commands
    private var rootCommand: Command? = null;

    private fun <T: CommandGroupBase> initCommandGroup(commandGroup: T, init: T.() -> Unit): T {
        commandGroup.init()

        // Make this command the root command
        println("Adding root command")
        rootCommand = commandGroup;

        return commandGroup
    }

    // Add a SequentialCommandGroup
    fun sequential(init: SequentialGroup.() -> Unit): SequentialGroup =
        initCommandGroup(SequentialGroup(), init)

    // Add a ParallelCommandGroup
    fun parallel(init: ParallelGroup.() -> Unit): ParallelGroup =
        initCommandGroup(ParallelGroup(), init)

    // Add a ParallelRaceGroup
    fun parallelRace(init: ParallelRaceGroup.() -> Unit): ParallelRaceGroup =
        initCommandGroup(ParallelRaceGroup(), init)

    fun parallelDeadline(init: ParallelDeadlineGroup.() -> Unit): ParallelDeadlineGroup =
        initCommandGroup(ParallelDeadlineGroup(), init)

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