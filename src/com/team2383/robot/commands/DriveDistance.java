package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
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

	private class NullPIDOutput implements PIDOutput {

		@Override
		public void pidWrite(double output) {

		}

	}

	public DriveDistance(double velocity, double distance, Gear gear, boolean brake) {
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
		headingController = new PIDController(Constants.driveHeadingMaintainP, Constants.driveHeadingMaintainI,
				Constants.driveHeadingMaintainD, Constants.driveHeadingMaintainF, navX, new NullPIDOutput());
		headingController.setInputRange(-180.0, 180.0);
		headingController.setOutputRange(-0.5, 0.5);
		headingController.setContinuous();
		headingController.setAbsoluteTolerance(Constants.driveHeadingMaintainTolerance);
		headingController.setSetpoint(0);

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
		if (Math.abs(distanceController.getError()) <= Constants.drivePositionTolerance) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		lastCheck = this.timeSinceInitialized();
		return timeAtSetpoint >= Constants.pidSetpointWait;
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
