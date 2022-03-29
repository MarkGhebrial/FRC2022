package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.OI
import frc.robot.subsystems.ClimberSubsystem

class ClimbManual(private val climber: ClimberSubsystem) : CommandBase() {
    override fun execute() {
        climber.setClimberPower(OI.operatorController.leftYWithDeadband)
    }
}