package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.util.FiringSolution;

/**
 * Activate the shooter, firing cargo if the condition evalutes to true
 */
public class Shoot extends CommandBase {

    private BooleanSupplier shootCondition;
    private FiringSolution solution;

    private ShooterSubsystem shooter;
    private IndexerSubsystem indexer;

    public Shoot(BooleanSupplier shootCondition, FiringSolution solution, ShooterSubsystem shooter, IndexerSubsystem indexer) {
        this.shootCondition = shootCondition;
        this.solution = solution;

        this.shooter = shooter;
        this.indexer = indexer;

        addRequirements(shooter, indexer);
    }

    @Override
    public void initialize() {
        shooter.goToFiringSolution(solution);
    }

    @Override
    public void execute() {
        // If the flywhheel is up to speed and the condition is true
        if (shooter.isFlywheelUpToSpeed() && shootCondition.getAsBoolean()) {
            indexer.activateConveyor();
            indexer.activateGateWheel();
        } else {
            indexer.deactivateConveyor();
            indexer.deactivateGateWheel();
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopFlywheel();
        indexer.deactivateConveyor();
        indexer.deactivateGateWheel();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
