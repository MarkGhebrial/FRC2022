package frc.robot;

public class UnitConversions {
    public static class Shooter {
        public static double flywheelEncoderTicksPer100msToRPM (double encoderTicksPer100ms) {
            return (encoderTicksPer100ms / 2048.0 * Constants.Shooter.MAIN_FLYWHEEL_GEAR_RATIO) * (1.0*10.0) * (1.0 * 60.0);
        }

        public static double flywheelRPMToEncoderTicksPer100ms (double RPM) {
            return RPM / flywheelEncoderTicksPer100msToRPM(1);
        }
    }
}
