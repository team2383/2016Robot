package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import java.util.function.DoubleSupplier;

import com.team2383.ninjaLib.NullPIDOutput;
import com.team2383.robot.Constants;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

public class TeleopDriveStraight extends Command {
	private final DoubleSupplier leftStick;
	private final PIDController headingController;

	public TeleopDriveStraight(DoubleSupplier leftStick) {
		super("Teleop Drive");
		requires(drivetrain);
		this.leftStick = leftStick;
		headingController = new PIDController(Constants.driveTeleopHeadingMaintainP,
				Constants.driveTeleopHeadingMaintainI, Constants.driveTeleopHeadingMaintainD,
				Constants.driveTeleopHeadingMaintainF, navX, new NullPIDOutput());
		headingController.setInputRange(-180.0, 180.0);
		headingController.setOutputRange(-1.0, 1.0);
		headingController.setContinuous();
		headingController.setAbsoluteTolerance(Constants.driveHeadingMaintainTolerance);
		headingController.setSetpoint(0);
	}

	@Override
	protected void initialize() {
		navX.reset();
		headingController.enable();
	}

	@Override
	protected void execute() {
		if (this.timeSinceInitialized() > 0.1) {
			drivetrain.arcade(leftStick.getAsDouble(), headingController.get());
		} else {
			drivetrain.arcade(leftStick.getAsDouble(), 0);
			System.out.println("Waiting for reset " + this.timeSinceInitialized());
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
		headingController.disable();
	}

	@Override
	protected void interrupted() {
		drivetrain.tank(0, 0);
		headingController.disable();
	}
}
