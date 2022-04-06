package frc.robot.commands.auto

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.ScheduleCommand
import edu.wpi.first.wpilibj2.command.StartEndCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.commands.drive.FollowTrajectory
import frc.robot.commands.shoot.AutonomousShoot
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.IndexerSubsystem
import frc.robot.subsystems.IntakeSubsystem
import frc.robot.subsystems.ShooterSubsystem
import frc.robot.util.FiringSolution
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group

class FiveBallAuto(
    drive: DriveSubsystem,
    indexer: IndexerSubsystem,
    intake: IntakeSubsystem,
    shooter: ShooterSubsystem
) : CommandCommand(
    group {
        fun intakeCommand(side: IntakeSubsystem.Side = IntakeSubsystem.Side.bothIntakes) = StartEndCommand(
            { intake.extendIntake(side) },
            { intake.retractIntake(side) }
        )

        +sequential {
            +ThreeBallAuto(drive, indexer, intake, shooter)

            +parallel {
                +FollowTrajectory(drive, "five-ball-auto-1", 7.0, 5.0, false)

                +sequential {
                    +WaitCommand(1.5)
                    +InstantCommand(indexer::startConveyor, indexer)
                    +InstantCommand({ intake.extendIntake(IntakeSubsystem.Side.leftIntake) }, intake)
                }
            }
            +WaitCommand(1.0)
            +InstantCommand(indexer::stopConveyor, indexer)
            +InstantCommand({ intake.retractIntake(IntakeSubsystem.Side.leftIntake) }, intake)

            +InstantCommand({ shooter.setFlywheelSpeed(2800.0) })
            +FollowTrajectory(drive, "five-ball-auto-2", 7.0, 5.0, false)

            +parallel {
                +AutonomousShoot(
                    0.0,
                    5.0,
                    FiringSolution(2800.0, true),
                    shooter, indexer
                )
                // Extend and retract the intake to agitate the cargo
                +timed {
                    +intakeCommand(IntakeSubsystem.Side.leftIntake)
                    startTime = 2.0; runTime = 0.5
                }
            }
        }
    }
)