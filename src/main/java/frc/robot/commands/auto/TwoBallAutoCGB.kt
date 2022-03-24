package frc.robot.commands.auto

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import friarLib2.commands.CommandGroupBuilder
import friarLib2.commands.RunUntilCommand

class TwoBallAutoCGB : CommandGroupBuilder() {
    init {
        sequential {
            operator fun Command.unaryPlus() {
                this@sequential.addCommands(this@unaryPlus)
            }

            +PrintCommand("Working")
            +parallel {
                this@parallel.addCommands(
                    WaitCommand(1.0),
                    PrintCommand("Hello from parallel")
                )
            }

            +PrintCommand("Working after one second")
        }
    }
}