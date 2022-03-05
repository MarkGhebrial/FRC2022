package frc.robot.commands.auto;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import friarLib2.commands.RunForTime;
import friarLib2.commands.TimedInstantCommand;

public class TaxiAndPreloadAuto extends SequentialCommandGroup {
    public TaxiAndPreloadAuto(DriveSubsystem drive, IndexerSubsystem indexer, ShooterSubsystem shooter) {
        addCommands(
            new RunForTime(new Shoot( // Shoot the preload
                () -> true,
                Constants.Shooter.LOW_HUB_FROM_FENDER,
                shooter, indexer
            ), 5),
            new TimedInstantCommand( // Back up at 2 m/s for one second
                1,
                () -> drive.setChassisSpeeds(new ChassisSpeeds(-2, 0, 0)),
                drive
            ),
            new InstantCommand(() -> drive.setChassisSpeeds(new ChassisSpeeds()), drive) // Stop moving
        );
    }
}
