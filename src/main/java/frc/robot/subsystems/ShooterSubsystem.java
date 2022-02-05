package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.FiringSolution;

import static frc.robot.Constants.Shooter.*;
import static frc.robot.UnitConversions.Shooter.*;

/**
 * Represents the robot's shooter
 * 
 * <p>
 * The mechanism contains a single flywheel with a fixed hood and a
 * pneumatically actuated "deflector" that redirects the shot to a
 * more horizontal trajectory
 */
public class ShooterSubsystem extends SubsystemBase {

    // Flywheel motors
    private WPI_TalonFX flywheelLeader;
    private WPI_TalonFX flywheelFollower;

    private DoubleSolenoid deflectorSolenoid;

    public ShooterSubsystem() {
        // Configure the flywheel motors
        flywheelLeader = new WPI_TalonFX(LEADER_MOTOR_ID);
        flywheelLeader.configFactoryDefault();
        flywheelLeader.setNeutralMode(NeutralMode.Coast);
        FLYWHEEL_MOTOR_PID.configureMotorPID(flywheelLeader);

        flywheelFollower = new WPI_TalonFX(FOLLOWER_MOTOR_ID);
        flywheelFollower.configFactoryDefault();
        flywheelFollower.setNeutralMode(NeutralMode.Coast);
        FLYWHEEL_MOTOR_PID.configureMotorPID(flywheelFollower);
        flywheelFollower.follow(flywheelLeader);

        deflectorSolenoid = new DoubleSolenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            HOOD_EXTENSION_SOLENOID_ID,
            HOOD_RETRACTION_SOLENOID_ID
        );
    }

    /**
     * Set the flywheel's closed loop setpoint
     * 
     * @param RPM Velocity in rotations per minute
     */
    public void setFlywheelSpeed (double RPM) {
        flywheelLeader.set(ControlMode.Velocity, flywheelRPMToEncoderTicksPer100ms(RPM));
    }

    /**
     * Deploy or retract the shot deflector
     * 
     * @param deployed If the deflector should be deployed or retracted
     */
    public void setDeflector (boolean deployed) {
        if (deployed) {
            deflectorSolenoid.set(Value.kForward);
        } else {
            deflectorSolenoid.set(Value.kReverse);
        }
    }

    public void goToFiringSolution (FiringSolution solution) {
        setFlywheelSpeed(solution.flywheelRPM);
        setDeflector(solution.deflector);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
