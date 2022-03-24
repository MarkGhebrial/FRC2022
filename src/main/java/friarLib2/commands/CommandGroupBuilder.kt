package friarLib2.commands

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.CommandGroupBase
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup

open class CommandGroupBuilder(): CommandBase() {
    // The command group that contains all other commands
    private var rootCommand: Command? = null;

    private fun <T: CommandGroupBase> initCommandGroup(commandGroup: T, init: T.() -> Unit): T {
        commandGroup.init()

        // If this is the first command to be added to the builder, then make it the root command
        //if (rootCommand == null) {
            println("Adding root command")
            rootCommand = commandGroup;
        //}

        return commandGroup
    }

    // Add a SequentialCommandGroup
    fun sequential(init: SequentialCommandGroup.() -> Unit) =
        initCommandGroup(SequentialCommandGroup(), init)

    // Add a ParallelCommandGroup
    fun parallel(init: ParallelCommandGroup.() -> Unit) =
        initCommandGroup(ParallelCommandGroup(), init)

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