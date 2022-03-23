package frc.robot.commands.shoot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.util.FiringSolution;

/**
 * Spin the flywheels and automatically shoot after the specified delay.
 * 
 * <p>
 * Designed for autonomous routines
 */
public class AutonomousShoot extends Shoot {

    private final Timer timer = new Timer();

    private final double runTime;

    /**
     * Create a new AutonomousShoot command 
     * @param shootDelay How long to wait before attempting to shoot
     * @param runTime How long to run the command
     * @param solution The flywheel speeds to run at
     * @param shooter
     * @param indexer
     */
    public AutonomousShoot(double shootDelay, double runTime, FiringSolution solution, ShooterSubsystem shooter, IndexerSubsystem indexer) {
        super(
            () -> false,
            solution,
            shooter,
            indexer
        );

        shootCondition = () -> timer.get() >= shootDelay;

        this.runTime = runTime;
    }
    
    @Override
    public void initialize() {
        super.initialize();

        timer.reset();
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.get() >= runTime;
    }
}
