package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import friarLib2.commands.RunUntilCommand;

/**
 * While this command is running, run the intake. When this command 
 * starts, tell the indexer to index a cargo.
 */
public class IntakeAndIndex extends ParallelCommandGroup {

    private Timer timer = new Timer();

    public IntakeAndIndex (IntakeSubsystem intake, IndexerSubsystem indexer) {
        timer.reset();
        timer.stop();
        
        addCommands(
            new ScheduleCommand( // We schedule the indexer's command separately so that it can run after this one terminates
                new RunUntilCommand(
                    new IndexOneCargo(indexer), // This will run either until it finishes or until five seconds have elapsed after the intake retracts
                    () -> timer.get() >= 5 // The timer only starts running after the intake retracts
                )
            ),
            new Intake(intake) // Deploy the intake
        );
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        timer.reset();
        timer.start();
    }
}
