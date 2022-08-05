package frc.robot.commands.climb

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.OI
import frc.robot.subsystems.ClimberSubsystem
import kotlin.math.pow

class ClimbManual(private val climber: ClimberSubsystem) : CommandBase() {
    init {
        addRequirements(climber)
    }

    override fun execute() {
        if (climber.isExtended) {
            // Squaring the joystick value makes motor power output non-linearly
            // related to joystick position, which should make low-speed adjustments
            // easier for the operator
            var motorPower = OI.operatorController.leftYWithDeadband.pow(2)
            if (OI.operatorController.leftYWithDeadband < 0) { motorPower = -motorPower }

            climber.setClimberPower(motorPower)
        } else {
            climber.setClimberPower(0.0)
        }
    }
}