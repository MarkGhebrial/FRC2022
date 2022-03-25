package frc.robot.commands.auto;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.shoot.Shoot;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.util.FiringSolution;
import friarLib2.commands.RunForTime;
import friarLib2.commands.TimedInstantCommand;

public class TaxiAndPreloadAuto extends SequentialCommandGroup {

    private Timer timer = new Timer();

    public TaxiAndPreloadAuto(FiringSolution solution, DriveSubsystem drive, IndexerSubsystem indexer, ShooterSubsystem shooter) {

        timer.reset();
        timer.start();

        addCommands(
            new RunForTime(new Shoot( // Shoot the preload
                () -> timer.get() >= 2,
                solution,
                shooter, indexer
            ), 5),
            new TimedInstantCommand( // Back up at 2 m/s for two seconds
                2,
                () -> drive.setChassisSpeeds(new ChassisSpeeds(-2, 0, 0)),
                drive
            ),
            new InstantCommand(drive::stopChassis, drive) // Stop moving
        );
    }

    @Override
    public void initialize() {
        super.initialize();
        timer.reset();
        timer.start();
    }
}
