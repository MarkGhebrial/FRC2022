package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.IMU;
import frc.robot.UnitConversions;

import static frc.robot.Constants.Climber.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ClimberSubsystem extends SubsystemBase {

    private final Solenoid solenoid;

    private final WPI_TalonFX leaderMotor;
    private final WPI_TalonFX followerMotor;

    private boolean isExtended;

    public ClimberSubsystem() {
        leaderMotor = new WPI_TalonFX(LEADER_MOTOR_ID);
        followerMotor = new WPI_TalonFX(FOLLOWER_MOTOR_ID);

        leaderMotor.configFactoryDefault();
        leaderMotor.setNeutralMode(NeutralMode.Brake);

        followerMotor.configFactoryDefault();
        followerMotor.setNeutralMode(NeutralMode.Brake);
        followerMotor.follow(leaderMotor);
        //TalonStatusFrames.configFollowerFrames(followerMotor);

        leaderMotor.setInverted(false);
        followerMotor.setInverted(true);

        //TalonStatusFrames.configDumbFrames(leaderMotor);
        //TalonStatusFrames.configDumbFrames(followerMotor);

        initEncoder();

        solenoid = new Solenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            CLIMBER_SOLENOID_ID
        );

        isExtended = solenoid.get();
    }

    private void initEncoder() {
        leaderMotor.setSelectedSensorPosition(UnitConversions.Climber.climberDegreesToEncoderTicks(CLIMBER_STARTING_ANGLE));
    }

    public boolean isExtended() {
        return isExtended;
    }

    public void setPiston(boolean extended) {
        isExtended = extended;
        solenoid.set(extended);
    }

    /**
     * Apply power to the climber, only if it's extended
     */
    public void setClimberPower(double percent) {
        if (isExtended()) {
            leaderMotor.set(ControlMode.PercentOutput, percent);
        }
    }

    /**
     * @return The climber's angle relative to the robot frame.
     */
    public double getClimberPosition() {
        return UnitConversions.Climber.climberEncoderTicksToDegrees(leaderMotor.getSelectedSensorPosition());
    }

    public double getClimberPositionRelativeToGround() {
        if (!isExtended()) {
            return -1;
        }
        return getClimberPosition() + IMU.getRobotPitch().getDegrees();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Climber Angle", getClimberPosition());
        SmartDashboard.putNumber("Climber Angle Relative to Ground", getClimberPositionRelativeToGround());
    }
}