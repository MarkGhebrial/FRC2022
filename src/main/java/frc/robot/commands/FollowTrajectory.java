package frc.robot.commands;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.IMU;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Makes the robot follow the pathweaver JSON passed in through the constructor
 */
public class FollowTrajectory extends CommandBase {

    private final DriveSubsystem drive;

    private HolonomicDriveController holonomicController;
    private Timer timer = new Timer();
    private PathPlannerTrajectory trajectory;

    /**
     * @param drive The drive subsystem
     */
    public FollowTrajectory (DriveSubsystem drive, String trajectoryJSON) {
        this.drive = drive;
        addRequirements(drive);

        // The holonomic controller uses the current robot pose and the target pose (from the trajectory) to calculate the required ChassisSpeeds to get to that location
        // See https://docs.wpilib.org/en/stable/docs/software/advanced-controls/trajectories/holonomic.html for more details
        holonomicController = new HolonomicDriveController(
            Constants.Drive.HOLONOMIC_CONTROLLER_PID_X, 
            Constants.Drive.HOLONOMIC_CONTROLLER_PID_Y, 
            Constants.Drive.HOLONOMIC_CONTROLLER_PID_THETA
        );
        // Set the range where the holonomic controller considers itself at its target location
        holonomicController.setTolerance(new Pose2d(new Translation2d(.09, .09), Rotation2d.fromDegrees(30)));

        trajectory = openTrajectoryFromJSON(trajectoryJSON); //Load the pathweaver trajectory
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drive.resetOdometry(trajectory.getInitialPose(), trajectory.getInitialPose().getRotation()); // Re-zero the robot's odometry
        timer.reset();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Trajectory.State goal = trajectory.sample(timer.get()); //Find the target pose for the current time

        //Use the holonomic drive controller to calculate the requred chassis speeds to follow the trajectory
        drive.setChassisSpeeds(holonomicController.calculate(
            drive.getRobotPose(), 
            goal, 
            IMU.getRobotYaw()
        ));
        
        SmartDashboard.putString("Holonomic controller error", drive.getRobotPose().minus(goal.poseMeters).toString());
        SmartDashboard.putNumber("Holonomic x error",  drive.getRobotPose().minus(goal.poseMeters).getTranslation().getX());
        SmartDashboard.putNumber("Holonomic y error",  drive.getRobotPose().minus(goal.poseMeters).getTranslation().getY());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return holonomicController.atReference() && timer.get() >= trajectory.getTotalTimeSeconds();
    }

    /**
     * Read the JSON output from pathweaver and convert it to a trajectory object
     *
     * @param JSONName the name of the JSON stored in the deploy/output directory, e.x. "bounceLeg1.wpilib.json"
     */
    private PathPlannerTrajectory openTrajectoryFromJSON (String JSONName) {
        return PathPlanner.loadPath(JSONName, Constants.Drive.MAX_AUTON_SPEED, Constants.Drive.MAX_AUTON_ACCELERATION);
    }
}