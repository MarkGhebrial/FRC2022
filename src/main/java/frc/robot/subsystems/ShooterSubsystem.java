package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    //private DoubleSolenoid deflectorSolenoid;

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

        flywheelLeader.setInverted(true);
        flywheelFollower.setInverted(false);

        /*deflectorSolenoid = new DoubleSolenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            HOOD_EXTENSION_SOLENOID_ID,
            HOOD_RETRACTION_SOLENOID_ID
        );*/
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
     * Stop the flywheel
     */
    public void stopFlywheel () {
        flywheelLeader.stopMotor();
    }

    /**
     * Get the speed of the flywheel in RPM
     * 
     * @return
     */
    public double getFlywheelRPM () {
        return flywheelEncoderTicksPer100msToRPM(flywheelLeader.getSelectedSensorVelocity());
    }

    /**
     * @return True if the flywheel is on and at its target velocity
     */
    public boolean isFlywheelUpToSpeed () {
        boolean isRunning = getFlywheelRPM() >= 50;

        boolean isUpToSpeed = Math.abs(flywheelEncoderTicksPer100msToRPM(flywheelLeader.getClosedLoopError())) <= FLYWHEEL_SPEED_TOLERANCE;

        System.out.println("Is running: " + isRunning + " ; Is up to speed: " + isUpToSpeed);

        return isRunning && isUpToSpeed;
    }

    /**
     * Deploy or retract the shot deflector
     * 
     * @param deployed If the deflector should be deployed or retracted
     */
    public void setDeflector (boolean deployed) {
        //deflectorSolenoid.set(deployed ? Value.kForward : Value.kReverse);
    }

    public void goToFiringSolution (FiringSolution solution) {
        setFlywheelSpeed(solution.flywheelRPM);
        setDeflector(solution.deflector);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Flywheel Speed", getFlywheelRPM());
        SmartDashboard.putNumber("Flywheel Setpoint", flywheelEncoderTicksPer100msToRPM(flywheelLeader.getClosedLoopTarget()));
        SmartDashboard.putNumber("Flywheel Error", flywheelEncoderTicksPer100msToRPM(flywheelLeader.getClosedLoopError()));
    }
}
