package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.RunCommand
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import frc.robot.subsystems.ClimberSubsystem
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group
import kotlin.math.abs

/**
 * Runs a bang-bang controller on the climber until its ground-relative
 * position meets the specified target
 */
class SetClimberPosition(position: Double, climber: ClimberSubsystem) : CommandCommand(
    group{
        +conditional({ climber.isExtended }) {
            +deadline {
                +RunCommand(
                    {
                        climber.setClimberPower(
                            if (climber.climberPositionRelativeToGround <= position) {
                                0.1
                            } else {
                                -0.1
                            }
                        )
                    }, climber
                )
                -WaitUntilCommand { abs(position - climber.climberPositionRelativeToGround) <= 5 }
            }
            -PrintCommand("Climber is not extended")
        }
    }
)