package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.Climber.*;

public class ClimberSubsystem extends SubsystemBase {

    private DoubleSolenoid climberSolenoid;

    public ClimberSubsystem() {
        climberSolenoid = new DoubleSolenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            EXTENSION_SOLENOID_ID, RETRACTION_SOLENOID_ID
        );

        retractClimber();
    }

    public void extendClimber() {
        climberSolenoid.set(Value.kForward);
    }

    public void retractClimber() {
        climberSolenoid.set(Value.kReverse);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
