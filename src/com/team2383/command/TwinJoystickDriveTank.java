package com.team2383.command;

import org.strongback.command.Command;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.TankDrive;

public class TwinJoystickDriveTank extends Command {
	private final FlightStick leftJoystick, rightJoystick;
	private final ContinuousRange leftSpeed, rightSpeed;
	private final TankDrive drive;

	public TwinJoystickDriveTank(TankDrive drive, FlightStick leftJoystick, FlightStick rightJoystick) {
		super(drive);

		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;
		this.drive = drive;

		leftSpeed = leftJoystick.getPitch().invert();
		rightSpeed = rightJoystick.getPitch().invert();
	}

	@Override
	public boolean execute() {
		drive.tank(leftSpeed.read(), rightSpeed.read(), true);
		return true;
	}
}
