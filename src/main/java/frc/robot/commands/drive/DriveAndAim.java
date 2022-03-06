package frc.robot.commands.drive;

import frc.robot.Constants;
import frc.robot.Vision;
import frc.robot.subsystems.DriveSubsystem;
import friarLib2.utility.Vector3309;

/**
 * Point at the target as the robot moves about the field
 */
public class DriveAndAim extends DriveTeleop {
    public DriveAndAim (DriveSubsystem drive) {
        super(drive);
    }

    /**
     * Use the data from the vision system to point towards the target
     */
    @Override
    protected double calculateRotationalSpeed (Vector3309 translationalSpeeds) {
        if (Vision.shooterCamera.hasTargets()) {
            return Constants.Drive.VISION_AIM_PID.calculate(Vision.shooterCamera.getBestTarget().getX());
        }
        
        // Use driver input if no target is found
        return super.calculateRotationalSpeed(translationalSpeeds);
    }
}
