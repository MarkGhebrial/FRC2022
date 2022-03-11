package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

/**
 * Slowly spin the intake rollers to agitate cargo
 */
public class SpinIntakeRollers extends CommandBase {
    
    private IntakeSubsystem intake;

    public SpinIntakeRollers(IntakeSubsystem intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.retractIntake();
        intake.setLeftIntakeRoller(true, 0.2);
        intake.setRightIntakeRoller(true, 0.2);
    }

    @Override
    public void end(boolean interrupted) {
        intake.retractIntake();
    }
}
