package frc.robot.commands.drive;

import edu.wpi.first.math.filter.SlewRateLimiter;
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

    private SlewRateLimiter xAccelLimiter;
    private SlewRateLimiter yAccelLimiter;

    public FieldRelativeTeleopControl(DriveSubsystem drive) {
        this.drive = drive;

        xAccelLimiter = new SlewRateLimiter(Constants.Drive.MAX_TELEOP_ACCELERATION);
        yAccelLimiter = new SlewRateLimiter(Constants.Drive.MAX_TELEOP_ACCELERATION);

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        xAccelLimiter.reset(0);
        yAccelLimiter.reset(0);
    }

    @Override
    public void execute() {
        Vector3309 translationalSpeeds = Vector3309.fromCartesianCoords(
            OI.leftStick.getXWithDeadband(), 
            -OI.leftStick.getYWithDeadband()).capMagnitude(1).scale(Constants.Drive.MAX_TELEOP_SPEED);

        // Limit the drivebase's acceleration to reduce wear on the swerve modules
        translationalSpeeds.setXComponent(xAccelLimiter.calculate(translationalSpeeds.getXComponent()));
        translationalSpeeds.setYComponent(yAccelLimiter.calculate(translationalSpeeds.getYComponent()));

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
