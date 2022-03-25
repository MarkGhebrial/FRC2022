package frc.robot.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class SetClimberPosition extends CommandBase {

    private static ClimberSubsystem climber;

    private static double degrees;

    public SetClimberPosition(double degrees, ClimberSubsystem climber) {
        this.degrees = degrees;

        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        climber.setClimberPosition(degrees);
    }

    @Override
    public boolean isFinished() {
        return climber.isClimberAtTargetPosition();
    }
}
