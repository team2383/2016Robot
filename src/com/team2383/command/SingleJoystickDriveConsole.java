package com.team2383.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.strongback.command.Command;
import org.strongback.components.Gyroscope;
import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.TankDrive;

import com.team2383.components.Speedometer;

public class SingleJoystickDriveConsole extends Command {
	private final FlightStick joystick;
	private final ContinuousRange turn, throttle;
	private double lastTurn, lastThrottle;
	private final TankDrive drive;
	private final Speedometer speedometer;
	private boolean braking;

	// max forward speed in ft/s where you can switch from forward to reverse
	private final static double MAX_REVERSE_SPEED = 1.0;

	public SingleJoystickDriveConsole(TankDrive drive, Speedometer speedometer, FlightStick joystick) {
		super(drive);

		this.joystick = joystick;
		this.drive = drive;
		this.speedometer = speedometer;
		
		turn = joystick.getYaw().map( turn -> {
			SmartDashboard.putNumber("turn", turn);
			if (turn > lastTurn+0.2) turn = lastTurn+0.2;
			else if (turn < lastTurn-0.2) turn = lastTurn-0.2;
			lastTurn = turn;
			return turn;
		});
		
		throttle = joystick.getPitch().invert().map( throttle -> {
			SmartDashboard.putNumber("throttleLast", lastThrottle);
			SmartDashboard.putNumber("throttle", throttle);
			if (throttle > lastTurn+0.2) throttle = lastThrottle+0.2;
			else if (throttle < lastTurn-0.2) throttle = lastThrottle-0.2;
			lastThrottle = throttle;
			SmartDashboard.putNumber("throttleAdj", lastThrottle);
			return throttle;
		});
	}

	@Override
	public boolean execute() {
		drive.arcade(throttle.read(), turn.read(), true);
		return true;
	}
}
