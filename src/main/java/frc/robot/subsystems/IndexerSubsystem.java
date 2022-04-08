package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.UnitConversions;
import frc.robot.util.TalonStatusFrames;
import friarLib2.math.RateOfChangeCalculator;

import static frc.robot.Constants.Indexer.*;

public class IndexerSubsystem extends SubsystemBase {

    private final WPI_TalonSRX conveyorMotor;
    private final WPI_TalonSRX gateMotor;

    private final RateOfChangeCalculator gateWheelCurrentRoC = new RateOfChangeCalculator();

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

        TalonStatusFrames.configDumbFrames(conveyorMotor);
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
        gateMotor.setVoltage(GATE_WHEEL_INDEXING_VOLTS);
    }

    /**
     * Start the gate wheel at the default speed for shooting
     */
    public void startGateWheelForShooting() {
        setGateWheel(GATE_WHEEL_SHOOTING_POWER);
    }

    public void setGateWheel(double percentOutput) {
        gateMotor.set(ControlMode.PercentOutput, percentOutput);
    }

    /**
     * Turn off the gate wheel
     */
    public void stopGateWheel() {
        gateMotor.stopMotor();
    }

    public double getGateWheelPosition() {
        return UnitConversions.Indexer.gateWheelEncoderTicksToDegrees(gateMotor.getSelectedSensorPosition());
    }

    /**
     * Roll the gate wheel by the specified amount
     * 
     * @param degrees The distance to move the wheel
     */
    public void rotateGateWheelByXDegrees(double degrees) {
        gateMotor.setSelectedSensorPosition(0);
        gateMotor.set(ControlMode.Position, UnitConversions.Indexer.gateWheelDegreesToEncoderTicks(degrees));
    }

    /**
     * @return The current being drawn by the gate wheel
     */
    public double getGateWheelSupplyCurrent() {
        return gateMotor.getSupplyCurrent();
    }

    public double getGateWheelSupplyCurrentRoC() {
        return gateWheelCurrentRoC.getRoC();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Gate wheel current", getGateWheelSupplyCurrent());

        gateWheelCurrentRoC.update(getGateWheelSupplyCurrent());

        SmartDashboard.putNumber("Gate wheel current RoC", gateWheelCurrentRoC.getRoC());
        SmartDashboard.putBoolean("Indexer Has Cargo", hasCargo());
        SmartDashboard.putNumber("Gate wheel closed loop error", gateMotor.getClosedLoopError());
        SmartDashboard.putNumber("Gate wheel closed loop error degrees", UnitConversions.Indexer.gateWheelEncoderTicksToDegrees(gateMotor.getClosedLoopError()));
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
