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

    private DoubleSolenoid leftIntakeSolenoid;
    private DoubleSolenoid rightIntakeSolenoid;

    private WPI_TalonSRX leftIntakeMotor;
    private WPI_TalonSRX rightIntakeMotor;
    
    public IntakeSubsystem() {
        leftIntakeSolenoid = new DoubleSolenoid(
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
        );

        leftIntakeMotor = new WPI_TalonSRX(LEFT_INTAKE_MOTOR_ID);
        rightIntakeMotor = new WPI_TalonSRX(RIGHT_INTAKE_MOTOR_ID);
    }

    /**
     * Extend the left intake without turning on the rollers
     */
    public void extendLeftIntake () {
        leftIntakeSolenoid.set(Value.kForward);
    }

    /**
     * Extend and activate the left intake
     */
    public void deployLeftIntake () {
        extendLeftIntake();
        leftIntakeMotor.set(ControlMode.Velocity, INTAKE_MOTOR_POWER);
    }

    /**
     * Retract the left intake and turn off its rollers
     */
    public void retractLeftIntake () {
        leftIntakeSolenoid.set(Value.kReverse);
        leftIntakeMotor.stopMotor();
    }

    /**
     * Deploy or retract the left intake
     * 
     * @param deployed If true, intake will deploy, otherwise, it'll retract
     */
    public void setLeftIntake (boolean deployed) {
        if (deployed) {
            deployLeftIntake();
        } else {
            retractLeftIntake();
        }
    }

    /**
     * Extend the right intake without turning on the rollers
     */
    public void extendRightIntake () {
        rightIntakeSolenoid.set(Value.kForward);
    }

    /**
     * Extend and activate the right intake
     */
    public void deployRightIntake () {
        extendRightIntake();
        rightIntakeMotor.set(ControlMode.Velocity, INTAKE_MOTOR_POWER);
    }

    /**
     * Retract the right intake and turn off its rollers
     */
    public void retractRightIntake () {
        rightIntakeSolenoid.set(Value.kReverse);
        rightIntakeMotor.stopMotor();
    }

    /**
     * Deploy or retract the right intake
     * 
     * @param deployed If true, intake will deploy, otherwise, it'll retract
     */
    public void setRightIntake (boolean deployed) {
        if (deployed) {
            deployRightIntake();
        } else {
            retractRightIntake();
        }
    }

    /**
     * Extend both intakes without turning them on
     */
    public void extendBothIntakes () {
        extendLeftIntake();
        extendRightIntake();
    }

    /**
     * Extend and activate both intakes
     */
    public void deployBothIntakes () {
        deployLeftIntake();
        deployRightIntake();
    }

    /**
     * Retract both intakes and turn of their rollers
     */
    public void retractBothIntakes () {
        retractLeftIntake();
        retractRightIntake();
    }

    /**
     * Deploy or retract the both intakes
     * 
     * @param deployed If true, intakes will deploy, otherwise, they'll retract
     */
    public void setBothIntakes (boolean deployed) {
        setLeftIntake(deployed);
        setRightIntake(deployed);
    }

    /**
     * Deploy or retract both intakes individually
     * 
     * @param leftDeployed Controls the left intake
     * @param rightDeployed Controls the right intake
     */
    public void setEachIntake (boolean leftDeployed, boolean rightDeployed) {
        setLeftIntake(leftDeployed);
        setRightIntake(rightDeployed);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
