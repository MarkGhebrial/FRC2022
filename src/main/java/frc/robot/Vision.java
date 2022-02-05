package frc.robot;

import friarLib2.vision.PhotonCameraWrapper;
import friarLib2.vision.VisionCamera;

/**
 * Container for the vision systems
 */
public class Vision {
    public static VisionCamera shooterCamera = new PhotonCameraWrapper("");

    /**
     * @return the distance in meters from the target
     */
    public static double getDistanceFromTarget () {
        return shooterCamera.getBestTarget().getPose().getX();
    }
}