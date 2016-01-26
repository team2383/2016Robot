package com.team2383.command;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.TankDrive;

public class GamepadDriveTank extends Command {
	private final Gamepad gamepad;
	private final ContinuousRange leftSpeed, rightSpeed;
	private final TankDrive drive;

	public GamepadDriveTank(TankDrive drive, Gamepad gamepad) {
		super(drive);

		this.gamepad = gamepad;
		this.drive = drive;

		leftSpeed = gamepad.getLeftY();
		rightSpeed = gamepad.getRightY();
	}

	@Override
	public boolean execute() {
		drive.tank(leftSpeed.read(), rightSpeed.read(), true);
		return true;
	}
}
