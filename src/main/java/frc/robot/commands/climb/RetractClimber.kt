package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.Constants
import frc.robot.subsystems.ClimberSubsystem
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group

class RetractClimber(climber: ClimberSubsystem) : CommandCommand(
    group {
        +sequential {
            +SetClimberPosition(Constants.Climber.CLIMBER_STARTING_ANGLE, climber)
            +InstantCommand({ climber.setPiston(false) }, climber)
        }
    }
)