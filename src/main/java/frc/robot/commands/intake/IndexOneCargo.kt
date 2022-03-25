package frc.robot.commands.intake

import frc.robot.subsystems.IndexerSubsystem
import edu.wpi.first.wpilibj2.command.ConditionalCommand
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.WaitUntilCommand
import frc.robot.Constants

/**
 * Run the indexer's gate wheel until a cargo has been indexed
 *
 *
 * TODO: This is not very effective. Simply running the conveyor at half
 * power is enough to reliably wedge the cargo in place, so we don't need a
 * complex current-sensing solution like this one.
 */
class IndexOneCargo(private val indexer: IndexerSubsystem) : ConditionalCommand(
    WaitCommand(0.0),  // Don't do anything if the indexer already has a cargo
    SequentialCommandGroup(
        InstantCommand({
            // Turn on the indexer
            indexer.startConveyor()
            indexer.startGateWheelForIndexing()
        }),
        WaitCommand(0.1),
        WaitUntilCommand { indexer.gateWheelSupplyCurrent > Constants.Indexer.GATE_WHEEL_CURRENT_THRESHOLD },  // Once a cargo has contacted the rollers, the current drawn by the motor should increase
        InstantCommand({
            indexer.stopConveyor()
            indexer.rotateGateWheelByXDegrees(Constants.Indexer.GATE_WHEEL_INDEXING_DEGREES) // Quickly spin the roller by a few rotations to "suck in" the cargo and hold it in place
        })
    ),
    { indexer.hasCargo() }
) {

    init {
        addRequirements(indexer)
    }

    override fun end(interrupted: Boolean) {
        super.end(interrupted)
        indexer.stopConveyor()
        if (interrupted) {
            indexer.stopGateWheel()
        }
    }
}