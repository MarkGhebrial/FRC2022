package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.Intake.*;

/**
 * Represents the robot's dual intakes.
 * 
 * <p>
 * Each intake has a double solenoid connected to two pistons and Talon 
 * SRX connected to a BAG motor.
 */
public class IntakeSubsystem extends SubsystemBase {

    //private DoubleSolenoid leftIntakeSolenoid;
    //private DoubleSolenoid rightIntakeSolenoid;

    private WPI_TalonSRX leftIntakeMotor;
    private WPI_TalonSRX rightIntakeMotor;
    
    public IntakeSubsystem() {
        /*leftIntakeSolenoid = new DoubleSolenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            LEFT_INTAKE_EXTENSION_SOLENOID_ID,
            LEFT_INTAKE_RETRACTION_SOLENOID_ID
        );

        rightIntakeSolenoid = new DoubleSolenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            RIGHT_INTAKE_EXTENSION_SOLENOID_ID,
            RIGHT_INTAKE_RETRACTION_SOLENOID_ID
        );*/

        leftIntakeMotor = new WPI_TalonSRX(LEFT_INTAKE_MOTOR_ID);
        rightIntakeMotor = new WPI_TalonSRX(RIGHT_INTAKE_MOTOR_ID);
    }

    /**
     * Extend the specified intake(s)
     * 
     * @param side Which intake (or both) to extend
     * @param activateRollers Rollers will turn on of true, stay off if false
     */
    public void extendIntake (Side side, boolean activateRollers) {
        if (side == Side.leftIntake || side == Side.bothIntakes) {
            //leftIntakeSolenoid.set(Value.kForward);
            setLeftIntakeRoller(activateRollers);
        }
        if (side == Side.rightIntake || side == Side.bothIntakes) {
            //rightIntakeSolenoid.set(Value.kForward);
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
            leftIntakeMotor.set(ControlMode.Velocity, INTAKE_MOTOR_POWER);
        } else {
            leftIntakeMotor.stopMotor();
        }
    }

    /**
     * Retract the left intake and turn off its rollers
     */
    private void retractLeftIntake () {
        //leftIntakeSolenoid.set(Value.kReverse);
        setLeftIntakeRoller(false);
    }

    /**
     * Turn on or off the right intake's roller
     */
    private void setRightIntakeRoller (boolean on) {
        if (on) {
            rightIntakeMotor.set(ControlMode.Velocity, INTAKE_MOTOR_POWER);
        } else {
            rightIntakeMotor.stopMotor();
        }
    }

    /**
     * Retract the right intake and turn off its rollers
     */
    private void retractRightIntake () {
        //rightIntakeSolenoid.set(Value.kReverse);
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
        // This method will be called once per scheduler run
    }
}
