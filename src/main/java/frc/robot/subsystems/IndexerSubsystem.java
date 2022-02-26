package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.UnitConversions;

import static frc.robot.Constants.Indexer.*;

public class IndexerSubsystem extends SubsystemBase {

    private WPI_TalonSRX conveyorMotor;
    private WPI_TalonSRX gateMotor;

    private boolean hasCargo = false;

    public IndexerSubsystem() {
        conveyorMotor = new WPI_TalonSRX(CONVEYOR_MOTOR_ID);
        conveyorMotor.configFactoryDefault();
        conveyorMotor.setNeutralMode(NeutralMode.Brake);

        gateMotor = new WPI_TalonSRX(GATE_WHEEL_MOTOR_ID);
        gateMotor.configFactoryDefault();
        gateMotor.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * @return If the indexer has a cargo indexed
     */
    public boolean hasCargo() {
        return hasCargo;
    }

    /**
     * Activate the conveyor at defult speed
     */
    public void activateConveyor() {
        conveyorMotor.set(ControlMode.PercentOutput, CONVEYOR_POWER);
    }

    /**
     * Turn off the conveyor
     */
    public void deactivateConveyor() {
        conveyorMotor.stopMotor();
    }

    /**
     * Activate or disable the conveyor
     * 
     * @param on If the conveyor should be on or off
     */
    public void setConveyor(boolean on) {
        if (on) {
            activateConveyor();
        } else {
            deactivateConveyor();
        }
    }

    /**
     * Activate the gate wheel at defult speed
     */
    public void activateGateWheel() {
        gateMotor.set(ControlMode.PercentOutput, GATE_WHEEL_POWER);
    }

    /**
     * Turn off the gate wheel
     */
    public void deactivateGateWheel() {
        gateMotor.stopMotor();
    }

    /**
     * Activate or disable the gate wheel
     * 
     * @param on If the gete wheel should be on or off
     */
    public void setGateWheel(boolean on) {
        if (on) {
            activateGateWheel();
        } else {
            deactivateGateWheel();
        }
    }

    /**
     * Roll the gate wheel by the specified amount
     * 
     * @param degrees The distance to move the wheel
     */
    public void rotateGateWheelByXDegrees(double degrees) {
        double targetPosition = getGateWheelPosition(degrees) + degrees;
        gateMotor.set(ControlMode.Position, UnitConversions.Indexer.gateWheelDegreesToEncoderTicks(targetPosition));
    }

    private double getGateWheelPosition(double degrees) {
        return UnitConversions.Indexer.gateWheelEncoderTicksToDegrees(gateMotor.getSelectedSensorPosition());
    }

    /**
     * @return The current being drawn by the gate wheel
     */
    public double getGateWheelSupplyCurrent() {
        return gateMotor.getSupplyCurrent();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
