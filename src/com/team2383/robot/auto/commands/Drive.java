/* Created Sat Feb 13 18:12:51 EST 2016 */
package com.team2383.robot.auto.commands;

import org.strongback.command.ControllerCommand;
import org.strongback.components.TalonSRX;
import org.strongback.control.Controller;
import org.strongback.drive.TankDrive;
import org.strongback.function.DoubleToDoubleFunction;

import com.team2383.robot.Config;
import com.team2383.robot.Robot;

import edu.wpi.first.wpilibj.Encoder;

public class Drive extends ControllerCommand {

	protected final DoubleToDoubleFunction leftApply;
	protected final DoubleToDoubleFunction rightApply;
	protected final TankDrive drive;

	double distance;
	double leftSpeed;
	double rightSpeed;
	Encoder wheelEncoder;
	
	public Drive(Controller sharedController, Runnable initializer, TankDrive drivetrain, double distance) {
		super(sharedController, initializer, drivetrain);
		this.leftApply = (x) -> x;
		this.rightApply = (x) -> -x;
		this.drive = drivetrain;
		this.wheelEncoder = Config.Encoders.wheelEncoder;
	}

	public Drive(Controller sharedController, Runnable initializer, DoubleToDoubleFunction leftApply,
			DoubleToDoubleFunction rightApply, TankDrive drivetrain, double distance) {
		super(sharedController, initializer, drivetrain);
		this.leftApply = leftApply;
		this.rightApply = rightApply;
		this.drive = drivetrain;
		this.wheelEncoder = Config.Encoders.wheelEncoder;
	}

	@Override
	public boolean execute() {
		double pidValue = controller.getValue();
		leftSpeed = leftApply.applyAsDouble(pidValue);
		rightSpeed = rightApply.applyAsDouble(pidValue);

		if (this.distance > 0) {
			drive.tank(leftSpeed, rightSpeed);

			wheelEncoder.setDistancePerPulse(Config.Constants.WHEEL_ENCODER_DISTANCE_PER_PULSE);

			if (wheelEncoder.getDistance() < (this.distance + 0.5)) {
				Robot.drive.tank(leftSpeed, rightSpeed);
				return false;
			} else {
				Config.Solenoids.shifter.retract();
				Robot.drive.tank(0.0, 0.0);
				return true;
			}
		} else {
			wheelEncoder.setDistancePerPulse(Config.Constants.WHEEL_ENCODER_DISTANCE_PER_PULSE);

			if (wheelEncoder.getDistance() < (this.distance - 0.5)) {
				Robot.drive.tank(leftSpeed, rightSpeed);
				return false;
			} else {
				Config.Solenoids.shifter.retract();
				Robot.drive.tank(0.0, 0.0);
				return true;
			}
		}
	}
}
