package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import com.team2383.robot.Constants;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives in a straight line for a distance in inches
 *
 * @author Matthew Alonso
 *
 */

public class DriveDistance extends Command {
	private final PIDController headingController;
	private final PIDController distanceController;

	public DriveDistance(double velocity, double distance) {
		super("Drive Distance");

		distanceController = new PIDController(Constants.drivePositionP, Constants.drivePositionI,
				Constants.drivePositionD, drivetrain, null);

		headingController = new PIDController(Constants.driveHeadingP, Constants.driveHeadingI, Constants.driveHeadingD,
				navX, null);

		requires(drivetrain);
	}

	@Override
	protected void initialize() {
		this.headingController.enable();
		this.distanceController.enable();
	}

	@Override
	protected void execute() {
		// NOTE: if weird shit happening with heading following
		// reverse sign of headingController output!
		drivetrain.arcade(distanceController.get(), headingController.get());
	}

	@Override
	protected boolean isFinished() {
		return distanceController.onTarget();
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
		this.headingController.disable();
		this.distanceController.disable();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
