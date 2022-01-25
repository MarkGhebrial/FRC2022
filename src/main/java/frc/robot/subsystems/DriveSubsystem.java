package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SwerveModule3309;
import friarLib2.utility.SwerveModule;

import static frc.robot.Constants.Drive.*;

public class DriveSubsystem extends SubsystemBase {

    private SwerveModule frontLeftModule;
    private SwerveModule frontRightModule;
    private SwerveModule backLeftModule;
    private SwerveModule backRightModule;

    private SwerveDriveOdometry swerveOdometry;
    private SwerveDriveKinematics swerveKinematics;
    private Pose2d currentRobotPose = new Pose2d();

    /**
     * Initialize the swerve modules and Kinematics/Odometry objects
     */
    public DriveSubsystem() {
        frontLeftModule = new SwerveModule3309(FRONT_LEFT_MODULE_DRIVE_MOTOR_ID, FRONT_LEFT_MODULE_ROTATION_MOTOR_ID, FRONT_LEFT_MODULE_ENCODER_ID, "Front left");
        frontRightModule = new SwerveModule3309(FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID, FRONT_RIGHT_MODULE_ROTATION_MOTOR_ID, FRONT_RIGHT_MODULE_ENCODER_ID, "Front right");
        backLeftModule = new SwerveModule3309(BACK_LEFT_MODULE_DRIVE_MOTOR_ID, BACK_LEFT_MODULE_ROTATION_MOTOR_ID, BACK_LEFT_MODULE_ENCODER_ID, "Back left");
        backRightModule = new SwerveModule3309(BACK_RIGHT_MODULE_DRIVE_MOTOR_ID, BACK_RIGHT_MODULE_ROTATION_MOTOR_ID, BACK_RIGHT_MODULE_ENCODER_ID, "Back right");

        swerveKinematics = new SwerveDriveKinematics(
            FRONT_LEFT_MODULE_TRANSLATION,
            FRONT_RIGHT_MODULE_TRANSLATION,
            BACK_LEFT_MODULE_TRANSLATION,
            BACK_RIGHT_MODULE_TRANSLATION
        );

        swerveOdometry = new SwerveDriveOdometry(swerveKinematics, getRobotRotation());
    }

    public void setModuleStates (SwerveModuleState[] states) {
        frontLeftModule.setState(states[0]);
        frontRightModule.setState(states[1]);
        backLeftModule.setState(states[2]);
        backRightModule.setState(states[3]);
    }

    /**
     * Calculate and set the requred SwerveModuleStates for a given ChassisSpeeds
     * 
     * @param speeds
     */
    public void setChassisSpeeds (ChassisSpeeds speeds) {
        SwerveModuleState[] moduleStates = swerveKinematics.toSwerveModuleStates(speeds); //Generate the swerve module states
        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, SwerveModule3309.ABSOLUTE_MAX_DRIVE_SPEED);
        setModuleStates(moduleStates);
    }

    /**
     * Get the current robot pose according to dead-reckoning odometry
     * See https://docs.wpilib.org/en/stable/docs/software/kinematics-and-odometry/swerve-drive-odometry.html for more details
     * 
     * @return Current robot pose
     */
    public Pose2d getRobotPose () {
        return currentRobotPose;
    }

    /**
     * Use the IMU to read the robot's yaw
     * 
     * @return Rotation2d representing IMU's measured angle
     */
    public Rotation2d getRobotRotation () {
        double angle = /*imu.getAngle();*/ 0; // TODO: Add IMU code
        return Rotation2d.fromDegrees(angle);
    }

    /**
     * Set the odometry readings
     * 
     * @param pose Pose to be written to odometry
     * @param rotation Roatation to be written to odometry
     */
    public void resetOdometry (Pose2d pose, Rotation2d rotation) {
        swerveOdometry.resetPosition(pose, rotation);
    }

    @Override
    public void periodic() {
        //Update the odometry using module states and chassis rotation
        currentRobotPose = swerveOdometry.update(
            getRobotRotation(),
            frontLeftModule.getState(),
            frontRightModule.getState(),
            backLeftModule.getState(),
            backRightModule.getState()
        );
    }
}
