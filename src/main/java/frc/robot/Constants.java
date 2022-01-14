// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static class Drive {
        /********** CAN ID's **********/
        public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR_ID = 1;
        public static final int FRONT_LEFT_MODULE_ROTATION_MOTOR_ID = 2;

        public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID = 3;
        public static final int FRONT_RIGHT_MODULE_ROTATION_MOTOR_ID = 4;

        public static final int BACK_LEFT_MODULE_DRIVE_MOTOR_ID = 5;
        public static final int BACK_LEFT_MODULE_ROTATION_MOTOR_ID = 6;

        public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR_ID = 7;
        public static final int BACK_RIGHT_MODULE_ROTATION_MOTOR_ID = 8;

        /********** Motor Translations **********/
        public static final Translation2d FRONT_LEFT_MODULE_TRANSLATION = new Translation2d(.5, .5);
        public static final Translation2d FRONT_RIGHT_MODULE_TRANSLATION = new Translation2d(.5, -.5);
        public static final Translation2d BACK_LEFT_MODULE_TRANSLATION = new Translation2d(-.5, .5);
        public static final Translation2d BACK_RIGHT_MODULE_TRANSLATION = new Translation2d(-.5, -.5);
    }
}
