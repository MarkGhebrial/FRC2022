package frc.robot.commands.auto

import edu.wpi.first.wpilibj2.command.InstantCommand
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
        fun intakeCommand(side: IntakeSubsystem.Side = IntakeSubsystem.Side.bothIntakes) = StartEndCommand(
            { intake.extendIntake(side) },
            { intake.retractIntake() },
            intake
        )

        +parallel {
            +sequential {
                // Spin up the flywheels now so that we don't have to wait for them when we are ready to shoot
                +InstantCommand({ shooter.setFlywheelSpeed(2800.0) }, shooter)

                +FollowTrajectory(drive, "three-ball-auto-1") // Line up to shoot
                +AutonomousShoot( // Shoot the preload
                    0.75,
                    2.0,
                    FiringSolution(2800.0, true),
                    shooter, indexer
                )

                +parallel { // Pick up two more balls
                    +FollowTrajectory(drive, "three-ball-auto-2")

                    +InstantCommand({ indexer.startConveyor() }, indexer)
                    +timed { // Intake the first cargo
                        +intakeCommand(IntakeSubsystem.Side.rightIntake)
                        startTime = 0.5; runTime = 1.75
                    }
                    +timed {
                        +intakeCommand(IntakeSubsystem.Side.leftIntake)
                        startTime = 3.0; runTime = 2.0
                    }
                }
                +parallel {
                    +AutonomousShoot(
                        0.75,
                        5.0,
                        FiringSolution(2800.0, true),
                        shooter, indexer
                    )
                    // Extend and retract the intake to agitate the cargo
                    +timed {
                        +intakeCommand()
                        startTime = 2.0; runTime = 1.0
                    }
                }
            }
        }
    }
)