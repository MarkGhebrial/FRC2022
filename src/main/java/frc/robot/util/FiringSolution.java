package frc.robot.util;

/**
 * Combines the flywheel speed and deflector state into one object
 */
public class FiringSolution {
    public final double flywheelRPM;
    public final boolean deflector;

    public FiringSolution (double flywheelRPM, boolean deflector) {
        this.flywheelRPM = flywheelRPM;
        this.deflector = deflector;
    }
}
