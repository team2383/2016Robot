package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import com.team2383.ninjaLib.NullPIDOutput;
import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives in a straight line for a distance in inches
 *
 * @author Matthew Alonso
 *
 */

public class DriveDistance extends Command {
	private final PIDController headingController;
	private final PIDController distanceController;
	private final boolean brake;
	private final Gear gear;
	private double lastCheck;
	private double timeAtSetpoint;
	private final double tolerance;
	private final double wait;
	private boolean finish = true;

	public DriveDistance(double velocity, double distance, Gear gear, boolean brake) {
		this(velocity, distance, Constants.driveTurnTolerance, gear, brake);
	}

	public DriveDistance(double velocity, double distance, Gear gear, boolean brake, boolean finish) {
		this(velocity, distance, Constants.driveTurnTolerance, gear, brake);
		this.finish = false;
	}

	public DriveDistance(double velocity, double distance, double tolerance, Gear gear, boolean brake) {
		this(velocity, distance, Constants.driveTurnTolerance, Constants.pidSetpointWait, gear, brake);
	}

	public DriveDistance(double velocity, double distance, double tolerance, double wait, Gear gear, boolean brake) {
		super("Drive Distance");
		this.gear = gear;
		this.brake = brake;

		distanceController = new PIDController(Constants.drivePositionP, Constants.drivePositionI,
				Constants.drivePositionD, Constants.drivePositionF, drivetrain, new NullPIDOutput());
		distanceController.setAbsoluteTolerance(Constants.drivePositionTolerance);
		distanceController.setSetpoint(distance);
		distanceController.setOutputRange(-velocity, velocity);

		SmartDashboard.putData("Distance Controller", distanceController);

		navX.reset();
		headingController = new PIDController(Constants.driveTurnP, Constants.driveTurnI, Constants.driveTurnD, 0.0,
				navX, new NullPIDOutput());
		headingController.setInputRange(-180.0, 180.0);
		headingController.setOutputRange(-1.0, 1.0); // changed from .5 if auto
														// is fucked
		headingController.setContinuous();
		headingController.setAbsoluteTolerance(Constants.driveTurnTolerance);
		headingController.setSetpoint(0);

		this.tolerance = tolerance;
		this.wait = wait;

		SmartDashboard.putData("MaintainHeading Controller", headingController);

		requires(drivetrain);
	}

	@Override
	protected void initialize() {
		this.headingController.enable();
		this.distanceController.enable();
		drivetrain.resetEncoders();
		navX.reset();
		drivetrain.shiftTo(gear);
		drivetrain.setBrake(brake);
	}

	@Override
	protected void execute() {
		if (this.timeSinceInitialized() > 0.1) {
			drivetrain.arcade(distanceController.get(), headingController.get());
		} else {
			System.out.println("Waiting for reset " + this.timeSinceInitialized());
		}
	}

	@Override
	protected boolean isFinished() {
		if (Math.abs(distanceController.getError()) <= tolerance) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		lastCheck = this.timeSinceInitialized();
		return finish && timeAtSetpoint >= wait;
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
		drivetrain.resetEncoders();
		this.headingController.disable();
		this.distanceController.disable();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
