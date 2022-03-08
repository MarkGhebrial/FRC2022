package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.Side;

public class Intake extends CommandBase {
    private IntakeSubsystem intake;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public Intake(IntakeSubsystem intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        // TODO: Doing controller logic/bindings in commands is the eighth deadly sin, so this needs to change
        if (OI.operatorController.getLeftBumper()) {
            intake.extendIntake(Side.leftIntake);
        } else {
            intake.retractIntake(Side.leftIntake);
        }

        if (OI.operatorController.getRightBumper()) {
            intake.extendIntake(Side.rightIntake);
        } else {
            intake.retractIntake(Side.rightIntake);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.retractIntake();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
