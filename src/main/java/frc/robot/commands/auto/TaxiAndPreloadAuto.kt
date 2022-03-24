package frc.robot.commands.auto

import frc.robot.util.FiringSolution
import frc.robot.subsystems.DriveSubsystem
import frc.robot.subsystems.IndexerSubsystem
import frc.robot.subsystems.ShooterSubsystem
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import friarLib2.commands.RunForTime
import frc.robot.commands.Shoot
import friarLib2.commands.TimedInstantCommand
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.InstantCommand

class TaxiAndPreloadAuto(
    solution: FiringSolution,
    drive: DriveSubsystem,
    indexer: IndexerSubsystem,
    shooter: ShooterSubsystem
) : SequentialCommandGroup() {

    private val timer = Timer()

    init {
        timer.reset()
        timer.start()

        addCommands(
            RunForTime(
                Shoot( // Shoot the preload
                    { timer.get() >= 2 },
                    solution,
                    shooter, indexer
                ), 5.0
            ),
            TimedInstantCommand( // Back up at 2 m/s for two seconds
                2.0,
                { drive.setChassisSpeeds(ChassisSpeeds(-2.0, 0.0, 0.0)) },
                drive
            ),
            InstantCommand({ drive.stopChassis() }, drive) // Stop moving
        )
    }

    override fun initialize() {
        super.initialize()
        timer.reset()
        timer.start()
    }
}