package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * Spin the flywheel and gate wheel backwards in order to recover any
 * cargo that lands on top of our robot
 */
public class ReverseShooter extends CommandBase {
    
    private IndexerSubsystem indexer;
    private ShooterSubsystem shooter;

    public ReverseShooter(IndexerSubsystem indexer, ShooterSubsystem shooter) {
        this.indexer = indexer;
        this.shooter = shooter;

        addRequirements(indexer, shooter);
    }

    @Override
    public void execute() {
        shooter.setFlywheelSpeed(-1000);
        indexer.setGateWheel(-.2);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopFlywheel();
        indexer.stopGateWheel();
    }
}
