package frc.robot.commands.drive;

import frc.robot.Constants;
import frc.robot.IMU;
import frc.robot.subsystems.DriveSubsystem;
import friarLib2.math.CTREModuleState;
import friarLib2.utility.Vector3309;

/**
 * Use the left joystick position to control the robot's direction of 
 * travel relative to the field. Robot heading will follow the
 * direction of travel.
 * 
 * <p>
 * i.e., the robot will move in whatever direction the stick is pushed,
 * and its heading will automatically change to point in that direction.
 */
public class PointInDirectionOfTravel extends DriveTeleop {

    public PointInDirectionOfTravel (DriveSubsystem drive) {
        super(drive);
    }

    /**
     * Use a PID controller to servo the robot heading to its direction of
     * travel
     */
    @Override
    protected double calculateRotationalSpeed (Vector3309 translationalSpeeds) {
        double currentHeading = IMU.getRobotYaw().getDegrees();

        // Do some math to calculate the appropriate target heading.
        double directionOfTravel = translationalSpeeds.getDegrees() + 90; // Add 90 degrees because robot heading 0 is forward, and mathematical 0 is to the right

        double adjustedDirection = CTREModuleState.placeInAppropriate0To360Scope(currentHeading, directionOfTravel);

        double rotationalSpeedDegs;
        if (translationalSpeeds.getMagnitude() == 0) { // If the robot is sitting still...
            rotationalSpeedDegs = 0; // ...then don't change the heading
        } else {
            rotationalSpeedDegs = Constants.Drive.HOLONOMIC_CONTROLLER_PID_THETA.calculate(currentHeading, adjustedDirection);
        }
        double rotationalSpeedRads = Math.toRadians(rotationalSpeedDegs);

        return rotationalSpeedRads;
    }
}
