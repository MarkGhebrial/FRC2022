package frc.robot;

public class UnitConversions {
    public static class Climber {
        public static double climberEncoderTicksToDegrees (double encoderTicks) {
            return (encoderTicks / 4096 * Constants.Climber.CLIMBER_GEAR_RATIO) * 360;
        }

        public static double climberDegreesToEncoderTicks (double degrees) {
            return degrees / climberEncoderTicksToDegrees(1);
        }
    }

    public static class Indexer {
        public static double gateWheelEncoderTicksToDegrees (double encoderTicks) {
            return (encoderTicks / 4096 * Constants.Indexer.GATE_WHEEL_GEAR_RATIO) * 360;
        }

        public static double gateWheelDegreesToEncoderTicks (double degrees) {
            return degrees / gateWheelEncoderTicksToDegrees(1);
        }

        public static double gateWheelEncoderTicksPer100msToRPM (double encoderTicksPer100ms) {
            return (gateWheelEncoderTicksToDegrees(encoderTicksPer100ms) / 360.0) * 10.0 * 60.0;
        }

        public static double gateWheelRPMToEncoderTicksPer100ms (double RPM) {
            return RPM / gateWheelEncoderTicksPer100msToRPM(1);
        }
    }

    public static class Shooter {
        public static double flywheelEncoderTicksPer100msToRPM (double encoderTicksPer100ms) {
            return (encoderTicksPer100ms / 2048.0 * Constants.Shooter.MAIN_FLYWHEEL_GEAR_RATIO) * (1.0 * 10.0) * (1.0 * 60.0);
        }

        public static double flywheelRPMToEncoderTicksPer100ms (double RPM) {
            return RPM / flywheelEncoderTicksPer100msToRPM(1);
        }
    }
}
