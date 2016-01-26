package com.team2383.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.strongback.command.Command;
import org.strongback.components.Gyroscope;
import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.TankDrive;

import com.team2383.components.Speedometer;

public class GamepadDriveConsole extends Command {
	private final Gamepad gamepad;
	private final ContinuousRange turn, throttle, brake, speed;
	private final TankDrive drive;
	private final Speedometer speedometer;
	private boolean braking;

	// max forward speed in ft/s where you can switch from forward to reverse
	private final static double MAX_REVERSE_SPEED = 1.0;

	public GamepadDriveConsole(TankDrive drive, Speedometer speedometer, Gamepad gamepad) {
		super(drive);

		this.gamepad = gamepad;
		this.drive = drive;
		this.speedometer = speedometer;

		turn = gamepad.getLeftX().invert();
		throttle = gamepad.getRightTrigger();
		brake = gamepad.getLeftTrigger().invert();
		braking = false;

		speed = brake.map(brake -> {
			// if we are braking

			SmartDashboard.putNumber("speed", speedometer.getSpeed());
			SmartDashboard.putNumber("Brake", brake);
			SmartDashboard.putNumber("throttle", throttle.read());

			if (brake < 0.0) {
				// if we are traveling faster than MAX_REVERSE_SPEED ft/s then
				// set motor drive to 0
				if (braking || speedometer.getSpeed() >= 1.0) {
					braking = true;
					return 0.0;

					// otherwise, use value of brake for speed (inverted value
					// range is -1.0 <-> 0.0)
				} else {
					return brake;
				}
				// no brake, return raw throttle values
			} else {
				braking = false;
				return throttle.read();
			}

		});

	}

	@Override
	public boolean execute() {
		drive.arcade(speed.read(), turn.read(), true);
		return true;
	}
}
