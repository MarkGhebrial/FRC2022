package frc.robot.commands.auto

import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.StartEndCommand
import frc.robot.Constants
import frc.robot.commands.Shoot
import frc.robot.commands.drive.FollowTrajectory
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.IndexerSubsystem
import frc.robot.subsystems.IntakeSubsystem
import frc.robot.subsystems.ShooterSubsystem
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group

class TwoBallAutoCGB(
    drive: DriveSubsystem,
    indexer: IndexerSubsystem,
    intake: IntakeSubsystem,
    shooter: ShooterSubsystem
): CommandCommand(
    group {
        +parallel {
            +sequential {
                +FollowTrajectory(drive,"two-ball-auto-1")
                +Shoot(
                    { timer.get() >= 5 },
                    Constants.Shooter.LOW_HUB_FROM_FENDER,
                    shooter, indexer
                ).withTimeout(5.0)
            }

            // Extend and retract the intake
            +timed {
                +StartEndCommand(
                    { intake.extendIntake(IntakeSubsystem.Side.leftIntake) },
                    { intake.retractIntake() },
                    intake
                )
                startTime = .2
                endTime = 5.0
            }
        }
    }.buildCommand()
)