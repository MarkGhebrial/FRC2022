package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.subsystems.ClimberSubsystem
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group

class DeployClimber(private val climber: ClimberSubsystem) : CommandCommand(
    group{
        +sequential {
            +InstantCommand(
                {
                    climber.setIsExtended(true)
                    climber.setClimberPower(0.25)
                },
                climber
            )
            +WaitCommand(1.5)
            +InstantCommand({ climber.setClimberPower(0.0) }, climber)
        }
    }
) {
    override fun end(interrupted: Boolean) {
        super.end(interrupted)

        if (interrupted) { climber.setClimberPower(0.0) }
    }
}