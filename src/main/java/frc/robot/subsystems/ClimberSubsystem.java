package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.Climber.*;

public class ClimberSubsystem extends SubsystemBase {

    private Solenoid climberSolenoid;

    public ClimberSubsystem() {
        climberSolenoid = new Solenoid(
            Constants.PCM_CAN_ID,
            Constants.PCM_TYPE,
            CLIMBER_SOLENOID_ID
        );

        retractClimber();
    }

    public void extendClimber() {
        climberSolenoid.set(true);
    }

    public void retractClimber() {
        climberSolenoid.set(false);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
