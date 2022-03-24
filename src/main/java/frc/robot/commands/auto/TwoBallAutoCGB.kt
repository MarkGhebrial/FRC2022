package frc.robot.commands.auto

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.RunCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import friarLib2.commands.CommandGroupBuilder

class TwoBallAutoCGB: CommandGroupBuilder() {
    private val timer: Timer = Timer()

    init {
        timer.stop()
        timer.reset()

        parallel {
            +sequential {
                +PrintCommand("Working")
                +parallel {
                    +WaitCommand(1.0)
                    +PrintCommand("Hello from parallel")
                }
                +PrintCommand("Working after one second")
            }
            +parallelDeadline {
                -WaitUntilCommand{ timer.get() >= 2 }
                +RunCommand({ println("Running ${timer.get()}") })
            }
        }
    }

    override fun initialize() {
        super.initialize()

        timer.reset()
        timer.start()
    }
}