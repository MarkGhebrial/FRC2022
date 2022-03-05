package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Pneumatics;

import static frc.robot.Constants.Intake.*;

/**
 * Represents the robot's dual intakes.
 * 
 * <p>
 * Each intake has a double solenoid connected to two pistons and Talon 
 * SRX connected to a BAG motor.
 */
public class IntakeSubsystem extends SubsystemBase {

    private Solenoid leftIntakeSolenoid;
    private Solenoid rightIntakeSolenoid;

    private WPI_TalonSRX leftIntakeMotor;
    private WPI_TalonSRX rightIntakeMotor;
    
    public IntakeSubsystem() {
        leftIntakeSolenoid = new Solenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            LEFT_INTAKE_SOLENOID_ID
        );

        rightIntakeSolenoid = new Solenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            RIGHT_INTAKE_SOLENOID_ID
        );

        leftIntakeMotor = new WPI_TalonSRX(LEFT_INTAKE_MOTOR_ID);
        rightIntakeMotor = new WPI_TalonSRX(RIGHT_INTAKE_MOTOR_ID);

        leftIntakeMotor.setInverted(true);
        rightIntakeMotor.setInverted(false);
    }

    /**
     * Extend the specified intake(s)
     * 
     * @param side Which intake (or both) to extend
     * @param activateRollers Rollers will turn on of true, stay off if false
     */
    public void extendIntake (Side side, boolean activateRollers) {
        if (side == Side.leftIntake || side == Side.bothIntakes) {
            leftIntakeSolenoid.set(true);
            setLeftIntakeRoller(activateRollers);
        }
        if (side == Side.rightIntake || side == Side.bothIntakes) {
            rightIntakeSolenoid.set(true);
            setRightIntakeRoller(activateRollers);
        }
    }

    /**
     * Extend the specified intake(s) and turn on their rollers
     * 
     * @param side Which intake (or both) to extend
     */
    public void extendIntake (Side side) {
        extendIntake(side, true);
    }

    /**
     * Extend and activate the rollers of both intakes
     */
    public void extendIntake () {
        extendIntake(Side.bothIntakes);
    }

    /**
     * Retract the specified intake(s) back into the robot frame, turning 
     * off their rollers
     * 
     * @param side Which intake (or both) to extend
     */
    public void retractIntake (Side side) {
        if (side == Side.leftIntake || side == Side.bothIntakes) {
            retractLeftIntake();
        }
        if (side == Side.rightIntake || side == Side.bothIntakes) {
            retractRightIntake();
        }
    }

    /**
     * Retract both intakes, turning off their rollers
     */
    public void retractIntake () {
        retractIntake(Side.bothIntakes);
    }

    /**
     * Turn on or off the left intake's roller
     */
    private void setLeftIntakeRoller (boolean on) {
        if (on) {
            leftIntakeMotor.set(ControlMode.PercentOutput, INTAKE_MOTOR_POWER);
        } else {
            leftIntakeMotor.stopMotor();
        }
    }

    /**
     * Retract the left intake and turn off its rollers
     */
    private void retractLeftIntake () {
        leftIntakeSolenoid.set(false);
        setLeftIntakeRoller(false);
    }

    /**
     * Turn on or off the right intake's roller
     */
    private void setRightIntakeRoller (boolean on) {
        if (on) {
            rightIntakeMotor.set(ControlMode.PercentOutput, INTAKE_MOTOR_POWER);
        } else {
            rightIntakeMotor.stopMotor();
        }
    }

    /**
     * Retract the right intake and turn off its rollers
     */
    private void retractRightIntake () {
        rightIntakeSolenoid.set(false);
        setRightIntakeRoller(false);
    }

    /**
     * Rpresents which intake(s) to deploy/retract
     */
    public static enum Side {
        leftIntake,
        rightIntake,
        bothIntakes
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Air Storage Pressure", Pneumatics.getStoragePSI());
        SmartDashboard.putBoolean("Compressor State", Pneumatics.getCompressorState());
    }
}
