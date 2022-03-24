package frc.robot.commands.auto

import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.PrintCommand
import edu.wpi.first.wpilibj2.command.RunCommand
import frc.robot.Constants
import frc.robot.commands.Shoot
import frc.robot.commands.drive.FollowTrajectory
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.IndexerSubsystem
import frc.robot.subsystems.IntakeSubsystem
import frc.robot.subsystems.ShooterSubsystem
import friarLib2.commands.builders.AutonomousBuilder

class TwoBallAutoCGB(
    drive: DriveSubsystem,
    indexer: IndexerSubsystem,
    intake: IntakeSubsystem,
    shooter: ShooterSubsystem
): AutonomousBuilder() {
    init {
        parallel {
            +sequential {
                +PrintCommand("Starting sequential")
                +FollowTrajectory(drive,"two-ball-auto-1")
                +Shoot(
                    { timer.get() >= 5 },
                    Constants.Shooter.LOW_HUB_FROM_FENDER,
                    shooter, indexer
                ).withTimeout(5.0)
            }
            +PrintCommand("Root parallel is OK")
            +RunCommand({ println("${timer.get()}") })

            // Extend and retract the intake
            +timed {
                +InstantCommand({ intake.extendIntake(IntakeSubsystem.Side.leftIntake) }, intake)
                    .andThen(
                        InstantCommand(intake::retractIntake, intake),
                        PrintCommand("Retracting intake at ${timer.get()}")
                    )
                +PrintCommand("Starting timed at ${timer.get()}")

                startTime = .2
                endTime = 5.0
            }
        }
    }
}