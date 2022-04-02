package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.IMU;
import frc.robot.UnitConversions;

import static frc.robot.Constants.Climber.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ClimberSubsystem extends SubsystemBase {

    private final WPI_TalonFX leaderMotor;
    private final WPI_TalonFX followerMotor;

    private boolean isExtended = false;

    public ClimberSubsystem() {
        leaderMotor = new WPI_TalonFX(LEADER_MOTOR_ID);
        followerMotor = new WPI_TalonFX(FOLLOWER_MOTOR_ID);

        leaderMotor.configFactoryDefault();
        leaderMotor.setNeutralMode(NeutralMode.Brake);

        followerMotor.configFactoryDefault();
        followerMotor.setNeutralMode(NeutralMode.Brake);
        followerMotor.follow(leaderMotor);

        leaderMotor.setInverted(false);
        followerMotor.setInverted(true);

        //leaderMotor.setSelectedSensorPosition(UnitConversions.Climber.climberDegreesToEncoderTicks(CLIMBER_STARTING_ANGLE));
        initEncoder();
    }

    public void initEncoder() {
        leaderMotor.setSelectedSensorPosition(UnitConversions.Climber.climberDegreesToEncoderTicks(CLIMBER_STARTING_ANGLE));
    }

    public boolean isExtended() {
        return isExtended;
    }

    public void setIsExtended(boolean isExtended) {
        this.isExtended = isExtended;
    }

    /**
     * Apply power to the climber, only if it's extended
     */
    public void setClimberPower(double percent) {
        if (isExtended() || percent >= 0.0) {
            leaderMotor.set(ControlMode.PercentOutput, percent);
        }
    }

    /**
     * @return The cliber's angle relative to the robot frame. Returns
     *         -1 if the climber is retracted.
     */
    public double getClimberPosition() {
        if (!isExtended()) {
            return -1;
        }
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