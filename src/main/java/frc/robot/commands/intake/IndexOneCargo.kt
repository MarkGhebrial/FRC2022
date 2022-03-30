package frc.robot.commands.intake

import frc.robot.subsystems.IndexerSubsystem
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.WaitUntilCommand

import friarLib2.commands.CommandCommand
import friarLib2.commands.builders.group
import kotlin.math.abs

/**
 * Run the indexer's conveyor until a cargo has been indexed
 */
class IndexOneCargo(private val indexer: IndexerSubsystem) : CommandCommand (
    group {
        val gateWheelStartingPosition = indexer.gateWheelPosition

        +conditional({ indexer.hasCargo() }) {
            +WaitCommand(0.0)
            -sequential {
                +InstantCommand({ indexer.startConveyor() }, indexer)
                +WaitUntilCommand { abs(indexer.gateWheelPosition - gateWheelStartingPosition) > 10 }
                +InstantCommand({ indexer.stopConveyor() }, indexer)
            }
        }
    }
) {
    override fun end(interrupted: Boolean) {
        super.end(interrupted)
        indexer.stopConveyor()
    }
}