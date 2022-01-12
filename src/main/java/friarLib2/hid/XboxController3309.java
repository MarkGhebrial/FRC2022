package friarLib2.hid;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Represents an Xbox controller, but with built in deadband calculation
 */
public class XboxController3309 extends XboxController {

    public double deadband;

    public XboxController3309 (final int port, double deadband) {
        super(port);

        this.deadband = deadband;
    }

    public double getLeftXWithDeadband () {
        return applyDeadband(super.getLeftX(), deadband);
    }

    public double getRightXWithDeadband () {
        return applyDeadband(super.getRightX(), deadband);
    }

    public double getLeftYWithDeadband () {
        return applyDeadband(super.getLeftY(), deadband);
    }

    public double getRightYWithDeadband () {
        return applyDeadband(super.getRightY(), deadband);
    }

    public double getLeftTriggerAxisWithDeadband () {
        return applyDeadband(super.getLeftTriggerAxis(), deadband);
    }

    public double getRightTriggerAxisWithDeadband () {
        return applyDeadband(super.getRightTriggerAxis(), deadband);
    }

    /**
     * Return zero if the absolute value of joystickValue is less than deadband, else, return joystickValue.
     * Ueseful for ensuring a zero value when a joystick is released.
     * 
     * @param joystickValue the value of the joystick axis
     * @param deadband the deadband to apply
     * @return the adjusted joystickValue
     */
    public static double applyDeadband (double joystickValue, double deadband) {
        return (Math.abs(joystickValue) > deadband) ? joystickValue : 0;
    }
}
