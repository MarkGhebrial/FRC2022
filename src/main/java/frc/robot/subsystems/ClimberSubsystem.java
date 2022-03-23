package frc.robot.subsystems;

import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants;
import frc.robot.IMU;
import frc.robot.UnitConversions;

import static frc.robot.Constants.Climber.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ClimberSubsystem extends ProfiledPIDSubsystem {

    private final Solenoid solenoid;

    private final WPI_TalonFX leaderMotor;
    private final WPI_TalonFX followerMotor;

    public ClimberSubsystem() {
        super(
            CLIMBER_PID_CONTROLLER,
            CLIMBER_STARTING_ANGLE
        );

        leaderMotor = new WPI_TalonFX(LEADER_MOTOR_ID);
        followerMotor = new WPI_TalonFX(FOLLOWER_MOTOR_ID);

        leaderMotor.configFactoryDefault();

        solenoid = new Solenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            CLIMBER_SOLENOID_ID
        );
    }

    public boolean isExtended() {
        return solenoid.get();
    }

    public void setPiston(boolean extended) {
        solenoid.set(extended);
    }

    /**
     * @return The cliber's angle relative to the robot frame. Returns
     *         -1 if the climber is retracted.
     */
    public double getClimberPosition() {
        if (isExtended()) {
            return -1;
        }
        return UnitConversions.Climber.climberEncoderTicksToDegrees(leaderMotor.getSelectedSensorPosition());
    }

    public double getClimberPositionRelativeToGround() {
        if (isExtended()) {
            return -1;
        }
        return getClimberPosition() + IMU.getRobotPitch().getDegrees();
    }

    @Override
    public void periodic() {
        // Only run the PID controller when the climber is extended
        if (isExtended()) {
            enable();
        } else {
            disable();
        }
    }

    @Override
    protected void useOutput(double output, State setpoint) {
        leaderMotor.setVoltage(output);
    }

    @Override
    protected double getMeasurement() {
        return getClimberPositionRelativeToGround();
    }
}
