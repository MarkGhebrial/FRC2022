package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.UnitConversions;

import static frc.robot.Constants.Indexer.*;

public class IndexerSubsystem extends SubsystemBase {

    private WPI_TalonSRX conveyorMotor;
    private WPI_TalonSRX gateMotor;

    public IndexerSubsystem() {
        conveyorMotor = new WPI_TalonSRX(CONVEYOR_MOTOR_ID);
        conveyorMotor.configFactoryDefault();
        conveyorMotor.setNeutralMode(NeutralMode.Brake);

        gateMotor = new WPI_TalonSRX(GATE_WHEEL_MOTOR_ID);
        gateMotor.configFactoryDefault();
        GATE_WHEEL_PID.configureMotorPID(gateMotor);
        gateMotor.setNeutralMode(NeutralMode.Brake);

        conveyorMotor.setInverted(true);
        gateMotor.setInverted(true);
    }

    /**
     * @return If the indexer has a cargo indexed
     */
    public boolean hasCargo() {
        return gateMotor.getControlMode() == ControlMode.Position;
    }

    /**
     * Start the conveyor at defult speed
     */
    public void startConveyor() {
        conveyorMotor.set(ControlMode.PercentOutput, CONVEYOR_POWER);
    }

    /**
     * Turn off the conveyor
     */
    public void stopConveyor() {
        conveyorMotor.stopMotor();
    }

    /**
     * Start the gate wheel at the default speed for indexing a cargo
     */
    public void startGateWheelForIndexing() {
        gateMotor.set(ControlMode.PercentOutput, GATE_WHEEL_INDEXING_POWER);
    }

    /**
     * Start the gate wheel at the default speed for shooting
     */
    public void startGateWheelForShooting() {
        gateMotor.set(ControlMode.PercentOutput, GATE_WHEEL_SHOOTING_POWER);
    }

    /**
     * Turn off the gate wheel
     */
    public void stopGateWheel() {
        gateMotor.stopMotor();
    }

    /**
     * Roll the gate wheel by the specified amount
     * 
     * @param degrees The distance to move the wheel
     */
    public void rotateGateWheelByXDegrees(double degrees) {
        double targetPosition = getGateWheelPosition() + degrees;
        gateMotor.set(ControlMode.Position, UnitConversions.Indexer.gateWheelDegreesToEncoderTicks(targetPosition));
    }

    private double getGateWheelPosition() {
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
        SmartDashboard.putNumber("Gate wheel current", getGateWheelSupplyCurrent());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
