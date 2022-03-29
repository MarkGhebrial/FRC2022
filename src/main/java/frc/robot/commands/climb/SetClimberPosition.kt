package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.RunCommand
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import frc.robot.subsystems.ClimberSubsystem
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group

/**
 * Runs a bang-bang controller on the climber until its ground-relative
 * position meets the specified target
 */
class SetClimberPosition(position: Double, climber: ClimberSubsystem) : CommandCommand(
    group{
        +deadline {
            +RunCommand(
                {
                    climber.setClimberPower(
                        if (position <= climber.climberPositionRelativeToGround) {
                            0.3
                        } else {
                            -0.3
                        }
                    )
                }, climber
            )
            -WaitUntilCommand{ position - climber.climberPositionRelativeToGround <= 2 }
        }
    }
)