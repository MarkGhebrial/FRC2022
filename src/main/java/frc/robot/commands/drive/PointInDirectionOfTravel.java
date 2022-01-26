package frc.robot.commands.drive;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.IMU;
import frc.robot.OI;
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
public class PointInDirectionOfTravel extends CommandBase {

    private DriveSubsystem drive;

    public PointInDirectionOfTravel(DriveSubsystem drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        Vector3309 translationalSpeeds = Vector3309.fromCartesianCoords(
            OI.leftStick.getXWithDeadband(), 
            -OI.leftStick.getYWithDeadband()).capMagnitude(1).scale(Constants.Drive.MAX_TELEOP_SPEED);

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

        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
            translationalSpeeds.getXComponent(), 
            translationalSpeeds.getYComponent(), 
            rotationalSpeedRads, 
            IMU.getRobotYaw());

        drive.setChassisSpeeds(speeds);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
