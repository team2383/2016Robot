package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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

	private class NullPIDOutput implements PIDOutput {

		@Override
		public void pidWrite(double output) {

		}

	}

	public DriveDistance(double velocity, double distance) {
		super("Drive Distance");

		distanceController = new PIDController(Constants.drivePositionP, Constants.drivePositionI,
				Constants.drivePositionD, drivetrain, new NullPIDOutput());
		distanceController.setAbsoluteTolerance(Constants.drivePositionTolerance);
		distanceController.setSetpoint(distance);
		distanceController.setOutputRange(-velocity, velocity);

		LiveWindow.addActuator("drivetrain", "distanceController", distanceController);

		navX.reset();
		headingController = new PIDController(Constants.driveHeadingMaintainP, Constants.driveHeadingMaintainI,
				Constants.driveHeadingMaintainD, navX, new NullPIDOutput());
		headingController.setInputRange(-180.0, 180.0);
		headingController.setOutputRange(-0.5, 0.5);
		headingController.setContinuous();
		headingController.setAbsoluteTolerance(Constants.driveHeadingTolerance);
		headingController.setSetpoint(0);
		headingController.setAbsoluteTolerance(Constants.driveHeadingTolerance);

		LiveWindow.addActuator("drivetrain", "headingController", headingController);

		requires(drivetrain);
	}

	@Override
	protected void initialize() {
		this.headingController.enable();
		this.distanceController.enable();
		drivetrain.resetEncoders();
		navX.reset();
		drivetrain.shiftTo(Gear.HIGH);
		drivetrain.enableBrake();
	}

	@Override
	protected void execute() {
		// NOTE: if weird shit happening with heading following
		// reverse sign of headingController output!
		SmartDashboard.putNumber("distance error", distanceController.getError());
		drivetrain.arcade(distanceController.get(), headingController.get());
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(distanceController.getError()) <= Constants.drivePositionTolerance;
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
