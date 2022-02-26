package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeAndIndex extends ParallelCommandGroup {
    public IntakeAndIndex (IntakeSubsystem intake, IndexerSubsystem indexer) {
        addCommands(
            new IndexOneCargo(indexer),
            new Intake(intake)
        );
    }
}
