package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Shoot;
import frc.robot.commands.drive.FollowTrajectory;
import frc.robot.commands.intake.IndexOneCargo;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem.Side;
import friarLib2.commands.RunForTime;

public class TwoBallAuto extends SequentialCommandGroup {
    public TwoBallAuto(DriveSubsystem drive, IndexerSubsystem indexer, IntakeSubsystem intake, ShooterSubsystem shooter) {
        addCommands(
            new InstantCommand(() -> intake.extendIntake(Side.leftIntake), intake), // Deploy the intake
            new ScheduleCommand(new IndexOneCargo(indexer)), // Start the indexer
            new FollowTrajectory(drive, "two-ball-auto-1"), // Pick up a cargo
            new InstantCommand(intake::retractIntake, intake),
            new RunForTime(new Shoot( // Shoot the cargo
                () -> true,
                Constants.Shooter.LOW_HUB_FROM_FENDER,
                shooter, indexer, intake
            ), 5)
        );
        
        addRequirements(drive, indexer, intake, shooter);
    }
}
