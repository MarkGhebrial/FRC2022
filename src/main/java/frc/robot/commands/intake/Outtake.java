package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.Side;

public class Outtake extends CommandBase {
    
    private IntakeSubsystem intake;

    public Outtake(IntakeSubsystem intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setIntake(Side.bothIntakes, true, false);
        intake.setLeftIntakeRoller(true, -Constants.Intake.INTAKE_MOTOR_POWER);
        intake.setRightIntakeRoller(true, -Constants.Intake.INTAKE_MOTOR_POWER);
    }

    @Override
    public void end(boolean interrupted) {
        intake.retractIntake();
    }
}
