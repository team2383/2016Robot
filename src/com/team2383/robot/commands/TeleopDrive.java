package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;

import java.util.function.DoubleSupplier;

import com.team2383.ninjaLib.CheesyDriveHelper;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopDrive extends Command {
	private final DoubleSupplier rightStick;
	private final DoubleSupplier leftStick;
	private final CheesyDriveHelper cdh;

	public TeleopDrive(DoubleSupplier leftStick, DoubleSupplier rightStick) {
		super("Teleop Drive");
		requires(drivetrain);
		this.leftStick = leftStick;
		this.rightStick = rightStick;
		this.cdh = new CheesyDriveHelper();
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		cdh.cheesyDrive(leftStick.getAsDouble(), rightStick.getAsDouble(), drivetrain.getGear() == Gear.HIGH);
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
