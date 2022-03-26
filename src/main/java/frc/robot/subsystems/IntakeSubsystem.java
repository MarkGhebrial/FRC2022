package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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

        leftIntakeMotor.setNeutralMode(NeutralMode.Brake);
        rightIntakeMotor.setNeutralMode(NeutralMode.Brake);

        leftIntakeMotor.setInverted(true);
        rightIntakeMotor.setInverted(false);
    }

    /**
     * Set the state of the specified intake(s)
     * 
     * @param side Which intake (or both) to set
     * @param deployed Whether to deploy the intake or not 
     * @param activateRollers Rollers will turn on of true, stay off if false
     */
    public void setIntake (Side side, boolean deployed, boolean activateRollers) {
        if (side == Side.leftIntake || side == Side.bothIntakes) {
            leftIntakeSolenoid.set(deployed);
            setLeftIntakeRoller(activateRollers);
        }
        if (side == Side.rightIntake || side == Side.bothIntakes) {
            rightIntakeSolenoid.set(deployed);
            setRightIntakeRoller(activateRollers);
        }
    }

    /**
     * Extend the specified intake(s)
     * 
     * @param side Which intake (or both) to extend
     * @param activateRollers Rollers will turn on of true, stay off if false
     */
    public void extendIntake (Side side, boolean activateRollers) {
        setIntake(side, true, activateRollers);
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
        setIntake(side, false, false);
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
    public void setLeftIntakeRoller (double power) {
        leftIntakeMotor.set(ControlMode.PercentOutput, power);
    }

    public void setLeftIntakeRoller (boolean on) {
        setLeftIntakeRoller(on ? INTAKE_MOTOR_POWER : 0);
    }

    /**
     * Turn on or off the right intake's roller
     */
    public void setRightIntakeRoller (double power) {
        rightIntakeMotor.set(ControlMode.PercentOutput, power);
    }

    public void setRightIntakeRoller (boolean on) {
        setRightIntakeRoller(on ? INTAKE_MOTOR_POWER : 0);
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
