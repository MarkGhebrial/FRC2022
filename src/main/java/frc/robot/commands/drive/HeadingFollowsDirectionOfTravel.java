package frc.robot.commands.drive;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.DriveSubsystem;
import friarLib2.math.CTREModuleState;

/**
 * Use the left joystick position to control the robot's direction of 
 * travel relative to the field. Robot heading will follow the
 * direction of travel.
 * 
 * <p>
 * i.e., the robot will move in whatever direction the stick is pushed,
 * and its heading will automatically change to point in that direction.
 */
public class HeadingFollowsDirectionOfTravel extends CommandBase {

    private DriveSubsystem drive;

    public HeadingFollowsDirectionOfTravel(DriveSubsystem drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        double xSpeed = Constants.Drive.MAX_TELEOP_SPEED * -OI.leftStick.getXWithDeadband();
        double ySpeed = Constants.Drive.MAX_TELEOP_SPEED * OI.leftStick.getYWithDeadband();

        double currentHeading = drive.getRobotRotation().getDegrees();

        // Do some math to calculate the appropriate target heading.
        double directionOfTravel = Math.toDegrees(Math.atan2(xSpeed, ySpeed)) + 90; // Add 90 degrees because robot heading 0 is forward, and mathematical 0 is to the right

        double adjustedDirection = CTREModuleState.placeInAppropriate0To360Scope(currentHeading, directionOfTravel);

        double rotationalSpeedDegs;
        if (xSpeed == 0 && ySpeed == 0) { // If the robot is sitting still...
            rotationalSpeedDegs = 0; // ...then don't change the heading
        } else {
            rotationalSpeedDegs = Constants.Drive.HOLONOMIC_CONTROLLER_PID_THETA.calculate(currentHeading, adjustedDirection);
        }
        double rotationalSpeedRads = Math.toRadians(rotationalSpeedDegs);

        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotationalSpeedRads, drive.getRobotRotation());

        drive.setChassisSpeeds(speeds);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
