package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.subsystems.ClimberSubsystem
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group

class DeployClimber(private val climber: ClimberSubsystem) : CommandCommand(
    group{
        +sequential {
            +InstantCommand({ climber.setPiston(true) }, climber)
            +InstantCommand({ climber.setClimberPower(0.2) }, climber)
            +WaitCommand(0.5)
            +InstantCommand({ climber.setClimberPower(0.0) }, climber)
        }
    }
) {
    override fun end(interrupted: Boolean) {
        if (interrupted) climber.setClimberPower(0.0)
    }
}

class RetractClimber(private val climber: ClimberSubsystem) : CommandCommand(
    InstantCommand({ climber.setPiston(false) }, climber)
)

class ToggleClimberExtension(climber: ClimberSubsystem) : CommandCommand(
    group {
        +conditional({ climber.isExtended }) {
            +RetractClimber(climber)
            -DeployClimber(climber)
        }
    }
)