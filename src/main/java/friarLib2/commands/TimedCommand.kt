package friarLib2.commands

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand

/**
 * A command that starts after a certain amount of time and ends after
 * some amount of time
 */
class TimedCommand(
    startTime: Double,
    runTime: Double,
    command: Command
): SequentialCommandGroup(
    WaitCommand(startTime),
    command.withTimeout(runTime)
)