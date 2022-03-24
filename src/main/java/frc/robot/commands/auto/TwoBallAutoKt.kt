package frc.robot.commands.auto

import edu.wpi.first.wpilibj2.command.*
import frc.robot.Constants
import frc.robot.commands.Shoot
import frc.robot.commands.drive.FollowTrajectory
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.IndexerSubsystem
import frc.robot.subsystems.IntakeSubsystem
import frc.robot.subsystems.ShooterSubsystem
import friarLib2.commands.RunForTime

class TwoBallAutoKt (drive: DriveSubsystem, indexer: IndexerSubsystem, intake: IntakeSubsystem, shooter: ShooterSubsystem) : SequentialCommandGroup() {
    init {
        addCommands(
            InstantCommand({ intake.extendIntake(IntakeSubsystem.Side.leftIntake) }, intake),
            FollowTrajectory(drive, "two-ball-auto-1"),
            InstantCommand(intake::retractIntake, intake),
            RunForTime(Shoot(
                { true },
                Constants.Shooter.LOW_HUB_FROM_FENDER,
                shooter, indexer
            ), 5.0),

            parallel {
                +PrintCommand("Hello there")
                +WaitCommand(1.0)
                +PrintCommand("Hello from one second")
            }
        )
    }

    fun parallel(init: ParallelCommandGroup.() -> Unit): ParallelCommandGroup {
        val commandGroup = ParallelCommandGroup();
        commandGroup.init()
        return commandGroup
    }

    operator fun Command.unaryPlus() {
        addCommands(this)
    }
}