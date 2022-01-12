package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import friarLib2.math.CTREModuleState;
import friarLib2.utility.PIDParameters;
import friarLib2.utility.SwerveModule;

public class SwerveModule3309 implements SwerveModule {
    /********* Constants *********/
    public static final double WHEEL_DIAMETER_INCHES = 3.8;
    public static final double DRIVE_GEAR_RATIO = 5.47;
    public static final PIDParameters DRIVE_PID_GAINS = new PIDParameters(.1, 0.0007, 0.1, "Swerve Drive PID");
    public static final PIDParameters ROTATION_PID_GAINS = new PIDParameters(.1, 0.002, 0, "Swerve Rotation PID");
    public static final double ABSOLUTE_MAX_DRIVE_SPEED = 4; // meters/sec

    public String name; // Used for diplaying values on SmartDashboard
    public WPI_TalonFX driveMotor;
    public WPI_TalonFX rotationMotor;

    private double lastAngle = 0.0;

    public SwerveModule3309 (int driveMotorID, int rotationMotorID, String name) {
        this.name = name;
        driveMotor = new WPI_TalonFX(driveMotorID);
        rotationMotor = new WPI_TalonFX(rotationMotorID);
        configMotors();
    }

    /**
     * Sets the motor PID values to those which will make the robot move the way we want.
     */
    public void configMotors () {
        driveMotor.configFactoryDefault();
        DRIVE_PID_GAINS.configureMotorPID(driveMotor);
        driveMotor.config_IntegralZone(0, 500);

        rotationMotor.configFactoryDefault();
        ROTATION_PID_GAINS.configureMotorPID(rotationMotor);
        rotationMotor.config_IntegralZone(0, 500);
    }

    /**
     * Set the state of the swerve module. Credit to team 364 for CTREModuleState
     * 
     * @param state the new target state for the module
     */
    public void setState (SwerveModuleState state) {
        
        state = CTREModuleState.optimize(state, getState().angle);

        double velocity = Conversions.mpsToEncoderTicksPer100ms(state.speedMetersPerSecond);
        driveMotor.set(ControlMode.Velocity, velocity);

        double angle = (Math.abs(state.speedMetersPerSecond) <= (ABSOLUTE_MAX_DRIVE_SPEED * 0.01)) ? lastAngle : state.angle.getDegrees(); // Prevent rotating module if speed is less than 1%. Prevents Jittering.
        rotationMotor.set(ControlMode.Position, Conversions.degreesToEncoderTicks(angle)); 
        lastAngle = angle;
    }

    /**
     * Get the physical position of the module
     * 
     * @return the module's position
     */
    public SwerveModuleState getState () {
        return new SwerveModuleState(
                Conversions.encoderTicksPer100msToMps(driveMotor.getSelectedSensorVelocity()), 
                Rotation2d.fromDegrees(Conversions.encoderTicksToDegrees(rotationMotor.getSelectedSensorPosition()))
        );
    }

    public static class Conversions {
        public static double mpsToEncoderTicksPer100ms (double mps) {
            double wheelDiameterMeters = Units.inchesToMeters(WHEEL_DIAMETER_INCHES);
            return mps * (1.0/(wheelDiameterMeters * Math.PI)) * DRIVE_GEAR_RATIO * (2048.0/1.0) * (1.0/10.0);
        }

        public static double encoderTicksPer100msToMps (double encoderTicksPer100ms) {
            return encoderTicksPer100ms / mpsToEncoderTicksPer100ms(1);
        }

        public static double degreesToEncoderTicks (double degrees) {
            return degrees * (2048.0 / 360.0);
        }

        public static double encoderTicksToDegrees (double encoderTicks) {
            return encoderTicks / degreesToEncoderTicks(1);
        }
    }
}