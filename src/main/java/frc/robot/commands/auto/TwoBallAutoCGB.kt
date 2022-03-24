package frc.robot.commands.auto

import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import friarLib2.commands.CommandGroupBuilder

class TwoBallAutoCGB: CommandGroupBuilder() {
    init {
        sequential {
            +PrintCommand("Working")
            +parallel {
                +WaitCommand(1.0)
                +PrintCommand("Hello from parallel")
            }
            +PrintCommand("Working after one second")
        }
    }
}