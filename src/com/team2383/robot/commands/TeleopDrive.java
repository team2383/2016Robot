package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopDrive extends Command {
	private final DoubleSupplier leftSpeed;
	private final DoubleSupplier rightSpeed;

	public TeleopDrive(DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {
		super("Teleop Drive");
		requires(drivetrain);
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		drivetrain.tank(leftSpeed.getAsDouble(), rightSpeed.getAsDouble());
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
