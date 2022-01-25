// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

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
    public static final double JOYSTICK_DEADBAND = 0.1;
    public static final double XBOX_DEADBAND = 0.05;

    public static final int PIGEON_IMU_ID = 13;

    /**
     * Constants for the Drivetrain
     */
    public static class Drive {
        /********** CAN ID's **********/
        public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR_ID = 1;
        public static final int FRONT_LEFT_MODULE_ROTATION_MOTOR_ID = 2;
        public static final int FRONT_LEFT_MODULE_ENCODER_ID = 9;

        public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID = 3;
        public static final int FRONT_RIGHT_MODULE_ROTATION_MOTOR_ID = 4;
        public static final int FRONT_RIGHT_MODULE_ENCODER_ID = 10;

        public static final int BACK_LEFT_MODULE_DRIVE_MOTOR_ID = 5;
        public static final int BACK_LEFT_MODULE_ROTATION_MOTOR_ID = 6;
        public static final int BACK_LEFT_MODULE_ENCODER_ID = 11;

        public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR_ID = 7;
        public static final int BACK_RIGHT_MODULE_ROTATION_MOTOR_ID = 8;
        public static final int BACK_RIGHT_MODULE_ENCODER_ID = 12;

        /********** Module Translations **********/
        public static final Translation2d FRONT_LEFT_MODULE_TRANSLATION = new Translation2d(.5, .5);
        public static final Translation2d FRONT_RIGHT_MODULE_TRANSLATION = new Translation2d(.5, -.5);
        public static final Translation2d BACK_LEFT_MODULE_TRANSLATION = new Translation2d(-.5, .5);
        public static final Translation2d BACK_RIGHT_MODULE_TRANSLATION = new Translation2d(-.5, -.5);

        /********** Autonomous Motion Envelope **********/
        public static final double MAX_AUTON_SPEED = 4; // Meters/second
        public static final double MAX_AUTON_ACCELERATION = 4; // Meters/second squared
        public static final double MAX_AUTON_ANGULAR_SPEED = 400; // Degrees/second
        public static final double MAX_AUTON_ANGULAR_ACCELERATION = 200; // Degrees/second squared

        /********** Holonomic Controller Gains **********/
        public static final PIDController HOLONOMIC_CONTROLLER_PID_X = new PIDController(9, 3, 0);
        public static final PIDController HOLONOMIC_CONTROLLER_PID_Y = new PIDController(9, 3, 0);
        public static final ProfiledPIDController HOLONOMIC_CONTROLLER_PID_THETA = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(MAX_AUTON_ANGULAR_SPEED, MAX_AUTON_ANGULAR_ACCELERATION));

        /********** Teleop Control Adjustment **********/
        public static final double MAX_TELEOP_SPEED = 6; // Meters/second
        public static final double MAX_TELEOP_ROTATIONAL_SPEED = Math.toRadians(700); // Radians/second
    }
}
