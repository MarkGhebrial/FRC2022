package frc.robot.commands.climb

import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import frc.robot.subsystems.ClimberSubsystem
import frc.robot.subsystems.DriveSubsystem
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group

class BeginClimb(climber: ClimberSubsystem, drive: DriveSubsystem): CommandCommand(
    group {
        if (climber.isExtended) { // Only climb if the climber has been extended
            +parallel {
                +SetClimberPosition(270.0 /*TODO: tune this*/, climber)

                // Drive backward so the hook can latch onto the bar
                +sequential {
                    +InstantCommand({
                        drive.setChassisSpeeds(ChassisSpeeds(-2.0, 0.0, 0.0))
                    }, drive)
                    +WaitUntilCommand { climber.climberPosition >= 120 }
                    +InstantCommand(drive::stopChassis, drive)
                }
            }
        } else { // If the climber is still retracted...
            +sequential {
                +InstantCommand({ climber.setPiston(true) }, climber) // ... then extend it
                +WaitCommand(0.5) // Give the piston enough time to extend before moving the climber
                +SetClimberPosition(90.0, climber)
            }
        }
    }
)