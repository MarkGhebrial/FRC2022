package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.FollowTrajectory;
import frc.robot.commands.shoot.AutonomousShoot;
import frc.robot.commands.shoot.Shoot;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem.Side;
import frc.robot.util.FiringSolution;

public class TwoBallAuto extends SequentialCommandGroup {
    public TwoBallAuto(DriveSubsystem drive, IndexerSubsystem indexer, IntakeSubsystem intake, ShooterSubsystem shooter) {
        addCommands(
            new InstantCommand(() -> intake.extendIntake(Side.leftIntake), intake), // Deploy the intake
            //new ScheduleCommand(new IndexOneCargo(indexer)), // Start the indexer
            new FollowTrajectory(drive, "two-ball-auto-1"), // Pick up a cargo
            new InstantCommand(intake::retractIntake, intake),
            new AutonomousShoot( // Shoot the cargo
                0.5, 5,
                new FiringSolution(2800, false),
                shooter, indexer
            )
        );
        
        addRequirements(drive, indexer, intake, shooter);
    }
}
