package frc.robot.util;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;

import static com.ctre.phoenix.motorcontrol.StatusFrameEnhanced.*;

/**
 * Contains convenience methods to set the CAN status frame frequencies
 * of Talon motor controllers.
 *
 * <p>
 * Credit to team 4414 for most of this code
 */
public class TalonStatusFrames {
    /**
     * Lower status frame frequencies for follower motors
     */
    public static void configFollowerFrames(BaseTalon motor) {
        motor.setStatusFramePeriod(Status_2_Feedback0, 255);
        motor.setStatusFramePeriod(Status_1_General, 255);
    }

    /**
     * Lower status frame frequencies for controllers whose "smart"
     * functionality is not being used
     */
    public static void configDumbFrames(BaseTalon motor) {
        motor.setStatusFramePeriod(Status_3_Quadrature, 255);
        motor.setStatusFramePeriod(Status_4_AinTempVbat, 255);
        motor.setStatusFramePeriod(Status_8_PulseWidth, 255);
        motor.setStatusFramePeriod(Status_10_Targets, 255);
        motor.setStatusFramePeriod(Status_12_Feedback1, 255);
        motor.setStatusFramePeriod(Status_13_Base_PIDF0, 255);
        motor.setStatusFramePeriod(Status_14_Turn_PIDF1, 255);
        motor.setStatusFramePeriod(Status_Brushless_Current, 255);

        motor.setStatusFramePeriod(Status_1_General, 255);
    }
}
