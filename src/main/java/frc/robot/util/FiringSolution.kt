package frc.robot.util

/**
 * Combines the flywheel speed and deflector state into one object
 */
data class FiringSolution(val flywheelRPM: Double, val deflector: Boolean)