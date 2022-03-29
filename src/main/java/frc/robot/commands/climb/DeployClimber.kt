package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.subsystems.ClimberSubsystem
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group

class DeployClimber(climber: ClimberSubsystem) : CommandCommand(
    group{
        sequential {
            +InstantCommand({ climber.setPiston(true) }, climber)
            +WaitCommand(0.5)
            +SetClimberPosition(90.0, climber)
        }
    }
)