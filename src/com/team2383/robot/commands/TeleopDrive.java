package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.navX;

import java.util.function.DoubleSupplier;

import com.team2383.ninjaLib.NullPIDOutput;
import com.team2383.robot.Constants;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

public class TeleopDrive extends Command {
	private final DoubleSupplier rightStick;
	private final DoubleSupplier leftStick;
	private double lastTurn;
	private PIDController headingController;

	public TeleopDrive(DoubleSupplier leftStick, DoubleSupplier rightStick) {
		super("Teleop Drive");
		requires(drivetrain);
		this.leftStick = leftStick;
		this.rightStick = rightStick;
		headingController = new PIDController(Constants.driveHeadingMaintainP, Constants.driveHeadingMaintainI,
				Constants.driveHeadingMaintainD, Constants.driveHeadingMaintainF, navX, new NullPIDOutput());
		headingController.setInputRange(-180.0, 180.0);
		headingController.setOutputRange(-1.0, 1.0);
		headingController.setContinuous();
		headingController.setAbsoluteTolerance(Constants.driveHeadingMaintainTolerance);
		headingController.setSetpoint(0);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double turn = rightStick.getAsDouble();
		if (turn == 0) {
			//rising edge
			if (lastTurn != 0) {
				navX.reset();
			}
			turn = headingController.get();
		}
		drivetrain.arcade(leftStick.getAsDouble(), turn);
		lastTurn = turn;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrain.tank(0, 0);
	}

	@Override
	protected void interrupted() {
		drivetrain.tank(0, 0);
	}
}
