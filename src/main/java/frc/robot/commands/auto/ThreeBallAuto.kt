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

class ThreeBallAuto (
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
                +FollowTrajectory(drive, "three-ball-auto-1") // Line up to shoot
                +AutonomousShoot( // Shoot the preload
                        0.75,
                        2.0,
                        FiringSolution(2800.0, true),
                        shooter, indexer
                )
                +FollowTrajectory(drive, "three-ball-auto-2") // Pick up two more balls
                +AutonomousShoot( // Shoot the preload
                    0.75,
                    5.0,
                    FiringSolution(2800.0, true),
                    shooter, indexer
                )
            }

            +timed { // Intake the first cargo
                +intakeCommand()
                startTime = 2.0
                runTime = 1.75
            }
            +timed {
                +intakeCommand()
                startTime = 3.0
                runTime = 2.0
            }
        }
    }
)