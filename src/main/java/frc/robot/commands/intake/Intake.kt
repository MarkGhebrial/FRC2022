package frc.robot.commands.intake

import frc.robot.subsystems.IntakeSubsystem
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.OI

class Intake(private val intake: IntakeSubsystem) : CommandBase() {
    init {
        addRequirements(intake)
    }

    override fun execute() {
        // TODO: Doing controller logic/bindings in commands is the eighth deadly sin, so this needs to change
        if (OI.operatorController.leftBumper) {
            intake.extendIntake(IntakeSubsystem.Side.leftIntake)
        } else {
            intake.retractIntake(IntakeSubsystem.Side.leftIntake)
        }

        if (OI.operatorController.rightBumper) {
            intake.extendIntake(IntakeSubsystem.Side.rightIntake)
        } else {
            intake.retractIntake(IntakeSubsystem.Side.rightIntake)
        }
    }

    override fun end(interrupted: Boolean) {
        intake.retractIntake()
    }

    override fun isFinished(): Boolean {
        return false
    }
}