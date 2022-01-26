package frc.robot.commands.drive;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.IMU;
import frc.robot.OI;
import frc.robot.subsystems.DriveSubsystem;
import friarLib2.utility.Vector3309;

/**
 * Use the left joystick position to control the robot's direction of 
 * travel relative to the field. Robot heading change is determined 
 * by moving the right joystick left and right.
 * 
 * <p>
 * i.e., the robot will move in whatever direction the stick is pushed,
 * regardless of its orientation on te field.
 */
public class FieldRelativeTeleopControl extends CommandBase {

    private DriveSubsystem drive;

    public FieldRelativeTeleopControl(DriveSubsystem drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        Vector3309 translationalSpeeds = Vector3309.fromCartesianCoords(
            OI.leftStick.getXWithDeadband(), 
            -OI.leftStick.getYWithDeadband()).capMagnitude(1).scale(Constants.Drive.MAX_TELEOP_SPEED);

        double rotationalSpeed = Constants.Drive.MAX_TELEOP_ROTATIONAL_SPEED * OI.rightStick.getXWithDeadband();

        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
            translationalSpeeds.getXComponent(), 
            translationalSpeeds.getYComponent(), 
            rotationalSpeed, 
            IMU.getRobotYaw());

        drive.setChassisSpeeds(speeds);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
