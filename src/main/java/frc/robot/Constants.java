// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.swerve.SwerveCANIDs;
import frc.robot.util.FiringSolution;
import friarLib2.utility.PIDParameters;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    /**
     * Constants that do not belong to a subsystem
     */
    public static final double JOYSTICK_DEADBAND = 0.07;
    public static final double XBOX_DEADBAND = 0.05;

    public static final int PCM_CAN_ID = 1;
    public static final int PIGEON_IMU_ID = 19;

    public static final PneumaticsModuleType PCM_TYPE = PneumaticsModuleType.REVPH;

    /**
     * Constants for the Drivetrain
     */
    public static class Drive {
        /********** CAN ID's **********/
        public static final SwerveCANIDs FRONT_LEFT_MODULE_IDS = new SwerveCANIDs(4, 6, 52);
        public static final SwerveCANIDs FRONT_RIGHT_MODULE_IDS = new SwerveCANIDs(3, 5, 53);
        public static final SwerveCANIDs BACK_LEFT_MODULE_IDS = new SwerveCANIDs(1, 2, 50);
        public static final SwerveCANIDs BACK_RIGHT_MODULE_IDS = new SwerveCANIDs(8, 7, 51);

        /********** Module Translations **********/
        public static final Translation2d FRONT_LEFT_MODULE_TRANSLATION = new Translation2d(0.34671, 0.23241);
        public static final Translation2d FRONT_RIGHT_MODULE_TRANSLATION = new Translation2d(0.34671, -0.23241);
        public static final Translation2d BACK_LEFT_MODULE_TRANSLATION = new Translation2d(-0.34671, 0.23241);
        public static final Translation2d BACK_RIGHT_MODULE_TRANSLATION = new Translation2d(-0.34671, -0.23241);

        /********** Autonomous Motion Envelope **********/
        public static final double MAX_AUTON_SPEED = 4; // Meters/second
        public static final double MAX_AUTON_ACCELERATION = 4; // Meters/second squared
        public static final double MAX_AUTON_ANGULAR_SPEED = 400; // Degrees/second
        public static final double MAX_AUTON_ANGULAR_ACCELERATION = 200; // Degrees/second squared

        /********** Holonomic Controller Gains **********/
        public static final PIDController HOLONOMIC_CONTROLLER_PID_X = new PIDController(9, 3, 0);
        public static final PIDController HOLONOMIC_CONTROLLER_PID_Y = new PIDController(9, 3, 0);
        public static final ProfiledPIDController HOLONOMIC_CONTROLLER_PID_THETA = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(MAX_AUTON_ANGULAR_SPEED, MAX_AUTON_ANGULAR_ACCELERATION));

        /******** PID Gains ********/
        public static final PIDController VISION_AIM_PID = new PIDController(0.1, 0, 0);

        /********** Teleop Control Adjustment **********/
        public static final double MAX_TELEOP_SPEED = 6; // Meters/second
        public static final double MAX_TELEOP_ROTATIONAL_SPEED = Math.toRadians(700); // Radians/second
        public static final double MAX_TELEOP_ACCELERATION = 5; // Maters/second squared
        public static final double MAX_TELEOP_DECELERATION = 9;
    }

    /**
     * Constants for the Indexer
     */
    public static class Indexer {
        /********** CAN ID's **********/
        public static final int CONVEYOR_MOTOR_ID = 17;
        public static final int GATE_WHEEL_MOTOR_ID = 18;

        /********** Tuning Constants **********/
        public static final double CONVEYOR_POWER = .2;
        public static final double GATE_WHEEL_SHOOTING_POWER = .75;
        public static final double GATE_WHEEL_INDEXING_POWER = .25;

        public static final double GATE_WHEEL_INDEXING_DEGREES = 0; // Rotate the gate by this much to index a cargo
        public static final double GATE_WHEEL_CURRENT_THRESHOLD = 3.95; // Amps

        public static final PIDParameters GATE_WHEEL_PID = new PIDParameters(0.1, 0, 0, "Gate wheel PID");

        /******** Physical Constants ********/
        public static final double GATE_WHEEL_GEAR_RATIO = 1.0 / 1.0; // Between the encoder and wheel, not between the motor and wheel
    }

    /**
     * Constants for the Intake
     */
    public static class Intake {
        /********** Solenoid PCM Ports **********/
        public static final int LEFT_INTAKE_SOLENOID_ID = 1;
        public static final int RIGHT_INTAKE_SOLENOID_ID = 2;

        /********** CAN ID's **********/
        public static final int LEFT_INTAKE_MOTOR_ID = 13;
        public static final int RIGHT_INTAKE_MOTOR_ID = 14;

        /********** Tuning Constants **********/
        public static final double INTAKE_MOTOR_POWER = 0.4;
    }

    /** 
     * Constants for the shooter 
     */
    public static class Shooter {
        /********** CAN ID's **********/
        public static final int LEADER_MOTOR_ID = 15;
        public static final int FOLLOWER_MOTOR_ID = 16;

        /********** PCM Ports **********/
        public static final int HOOD_SOLENOID_ID = 3;

        /******** PID Gains ********/
        public static final PIDParameters FLYWHEEL_MOTOR_PID = new PIDParameters(0.2, 0.001, 2, "Flywheel PID");
        public static final double FLYWHEEL_IZONE = 700;

        /********** Tuning Constants **********/
        public static final double FLYWHEEL_SPEED_TOLERANCE = 50; // RPM

        /******** Physical Constants ********/
        public static final double MAIN_FLYWHEEL_GEAR_RATIO = 18.0 / 24.0;

        /********** Firing Solutions **********/
        public static final FiringSolution LOW_HUB_FROM_FENDER = new FiringSolution(1000, true);
        public static final FiringSolution LOW_HUB_FROM_TARMAC = new FiringSolution(1850, true);

        public static final FiringSolution HIGH_HUB_FROM_FENDER = new FiringSolution(2150, false);
        public static final FiringSolution HIGH_HUB_FROM_TARMAC = new FiringSolution(2500, true);
        public static final FiringSolution HIGH_HUB_FROM_LAUNCHPAD = new FiringSolution(100, false);
    }
}
