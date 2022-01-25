package friarLib2.utility;

import edu.wpi.first.math.kinematics.SwerveModuleState;

/**
 * An interface representing any swerve module
 */
public interface SwerveModule {
    public void setState (SwerveModuleState state);
    public SwerveModuleState getState ();
    
    default public boolean steeringHasSlipped () {
        return false;
    }
}
