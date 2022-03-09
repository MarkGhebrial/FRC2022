package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Rotate the swerve modules so that we cannot be moved by defense
 */
public class LockDriveTrain extends CommandBase {
    
    private static final SwerveModuleState[] LOCK_STATES = {
        new SwerveModuleState(0, Rotation2d.fromDegrees(0)),
        new SwerveModuleState(0, Rotation2d.fromDegrees(0)),
        new SwerveModuleState(0, Rotation2d.fromDegrees(0)),
        new SwerveModuleState(0, Rotation2d.fromDegrees(0)) 
    };
    
    private DriveSubsystem drive;

    public LockDriveTrain(DriveSubsystem drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.setModuleStates(LOCK_STATES);
    }

    @Override
    public boolean isFinished() {
        return true; // Exit immediately
    }
}
