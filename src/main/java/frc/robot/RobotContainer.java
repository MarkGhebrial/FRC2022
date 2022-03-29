// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.auto.TaxiAndPreloadAuto;
import frc.robot.commands.auto.TwoBallAuto;
import frc.robot.commands.drive.DriveAndAim;
import frc.robot.commands.drive.DriveTeleop;
import frc.robot.commands.drive.PointInDirectionOfTravel;
import frc.robot.commands.intake.IntakeAndIndex;
import frc.robot.commands.intake.Outtake;
import frc.robot.commands.intake.SpinIntakeRollers;
import frc.robot.commands.shoot.ReverseShooter;
import frc.robot.commands.shoot.Shoot;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.util.FiringSolution;
import friarLib2.hid.LambdaTrigger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DriveSubsystem drive = new DriveSubsystem();
    public final ClimberSubsystem climber = new ClimberSubsystem();
    private final IndexerSubsystem indexer = new IndexerSubsystem();
    private final IntakeSubsystem intake = new IntakeSubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();

    private final SendableChooser<Command> autoChooser = new SendableChooser<Command>();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Add autos to SmartDashboard
        autoChooser.addOption("No auto", new WaitUntilCommand(0));
        autoChooser.addOption("Preload auto (low goal)", new TaxiAndPreloadAuto(Constants.Shooter.LOW_HUB_FROM_FENDER, drive, indexer, shooter));
        autoChooser.setDefaultOption("Preload auto (high goal)", new TaxiAndPreloadAuto(new FiringSolution(2800, false), drive, indexer, shooter));
        autoChooser.addOption("Two ball auto (hangar side)", new TwoBallAuto(drive, indexer, intake, shooter));
        SmartDashboard.putData(autoChooser);

        configureDefaultCommands();
        configureButtonBindings();
    }

    private void configureDefaultCommands() {
        drive.setDefaultCommand(new DriveTeleop(drive));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        new LambdaTrigger(() -> OI.leftStick.getTrigger())
            .whileActiveContinuous(new PointInDirectionOfTravel(drive));

        new LambdaTrigger(() -> OI.rightStick.getTrigger())
            .whileActiveContinuous(new DriveAndAim(drive));

        // Zero the IMU
        new LambdaTrigger(() -> OI.leftStick.getTop())
            .whenActive(new InstantCommand(IMU::zeroIMU));

        // Zero the modules
        new LambdaTrigger(() -> OI.leftStick.getPOV() != -1)
            .whenActive(new InstantCommand(drive::zeroModules, drive));

        // Intake
        new LambdaTrigger(() -> OI.operatorController.getLeftBumper() || OI.operatorController.getRightBumper())
            .whileActiveContinuous(new IntakeAndIndex(intake, indexer), false);

        new LambdaTrigger(() -> OI.operatorController.getLeftTriggerAxis() >= 0.5)
            .whileActiveContinuous(new SpinIntakeRollers(intake));

        // Outtake
        new LambdaTrigger(() -> OI.operatorController.getRightTriggerAxis() >= 0.5)
            .whileActiveContinuous(new Outtake(intake));

        // Extend the climber
        //new LambdaTrigger(() -> OI.rightStick.getPOV() != -1)
        //    .whenActive(new InstantCommand(climber::extendClimber, climber));

        // Retract the climber
        //new LambdaTrigger(() -> OI.rightStick.getTop())
        //    .whenActive(new InstantCommand(climber::retractClimber, climber));

        // Bind the operator's D-pad to various shooting locations
        //bindShootingCommand(Constants.Shooter.HIGH_HUB_FROM_FENDER, 0 ); // 0 is up, the values increase clockwise
        bindShootingCommand(Constants.Shooter.HIGH_HUB_FROM_TARMAC, 180); //90
        bindShootingCommand(Constants.Shooter.LOW_HUB_FROM_FENDER, () -> OI.leftStickLeftCluster.get() || OI.rightStickRightCluster.get()); //180
        //bindShootingCommand(Constants.Shooter.LOW_HUB_FROM_TARMAC, 270);

        //bindShootingCommand(Constants.Shooter.HIGH_HUB_FROM_LAUNCHPAD, 45);

        // Spin the flywheel in reverse in case a cargo falls on our robot
        new LambdaTrigger(() -> OI.operatorController.getStartButton())
            .whileActiveContinuous(new ReverseShooter(indexer, shooter));
    }

    /**
     * Bind a Shoot command with the specified FiringSolution to the 
     * specified angle on the operator's D-pad.
     * 
     * @param solution The firing solution to go to
     * @param dPadPosition The value returned by XboxController.getPOV()
     */
    private void bindShootingCommand(FiringSolution solution, int dPadPosition) {
        bindShootingCommand(solution, () -> OI.operatorController.getPOV() == dPadPosition);
    }

    private void bindShootingCommand(FiringSolution solution, BooleanSupplier condition) {
        new LambdaTrigger(condition)
            .whileActiveContinuous(
                new Shoot(
                    () -> OI.operatorController.getAButton() || OI.leftStickRightCluster.get() || OI.rightStickLeftCluster.get(), // Fire a cargo if this evaluates to true
                    solution, shooter, indexer), 
                true); // Set the command as interruptible
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
