package frc.robot.commands.auto

import edu.wpi.first.wpilibj2.command.StartEndCommand
import frc.robot.commands.drive.FollowTrajectory
import frc.robot.commands.shoot.AutonomousShoot
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.IndexerSubsystem
import frc.robot.subsystems.IntakeSubsystem
import frc.robot.subsystems.ShooterSubsystem
import frc.robot.util.FiringSolution
import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group

class TwoBallAuto(
    drive: DriveSubsystem,
    indexer: IndexerSubsystem,
    intake: IntakeSubsystem,
    shooter: ShooterSubsystem
): CommandCommand(
    group {
        fun intakeCommand() = StartEndCommand(
            { intake.extendIntake(IntakeSubsystem.Side.rightIntake) },
            { intake.retractIntake() },
            intake
        )

        +parallel {
            +sequential {
                +parallel {
                    +FollowTrajectory(drive, "two-ball-auto-1").withTimeout(10.0)
                    // Extend and retract the intake
                    +timed {
                        +intakeCommand()
                        startTime = 0.8
                        runTime = 1.95
                    }
                }
                +parallel {
                    +AutonomousShoot(
                        0.75, 5.0,
                        FiringSolution(2850.0, true),
                        shooter, indexer
                    )
                    +timed {
                        +intakeCommand()
                        startTime = 2.0
                        endTime = 3.0
                    }
                }
            }
        }
    }
)