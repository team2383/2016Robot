package com.team2383.ninjaLib;

import static com.team2383.robot.HAL.drivetrain;

import com.team2383.robot.Constants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * CheesyDriveHelper implements the calculations used in CheesyDrive, sending
 * power to the motors.
 */
public class CheesyDriveHelper {
	double oldWheel, quickStopAccumulator;
	private final double throttleDeadband = 0.02;
	private final double wheelDeadband = 0.02;

	public void cheesyDrive(double throttle, double wheel, boolean isHighGear) {
		double wheelNonLinearity;

		boolean isQuickTurn = Math.abs(throttle) < 0.1;
		wheel = handleDeadband(wheel, wheelDeadband);
		throttle = handleDeadband(throttle, throttleDeadband);

		double negInertia = wheel - oldWheel;
		oldWheel = wheel;

		if (isHighGear) {
			wheelNonLinearity = 0.6;
			// Apply a sin function that's scaled to make it feel better.
			wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
			wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
		} else {
			wheelNonLinearity = 0.5;
			// Apply a sin function that's scaled to make it feel better.
			wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
			wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
			wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
		}

		double leftSpeed, rightSpeed, overPower;
		double sensitivity;

		double angularPower;
		double linearPower;

		// Negative inertia!
		double negInertiaAccumulator = 0.0;
		double negInertiaScalar;
		if (isHighGear) {
			if (Math.abs(wheel) > 0.65) {
				negInertiaScalar = 6.5;
			} else {
				negInertiaScalar = 4.5;
			}
			sensitivity = Constants.driveTurnSensitivityHigh;
		} else {
			if (wheel * negInertia > 0) {
				negInertiaScalar = 2.5;
			} else {
				if (Math.abs(wheel) > 0.65) {
					negInertiaScalar = 5.5;
				} else {
					negInertiaScalar = 3.0;
				}
			}
			sensitivity = Constants.driveTurnSensitivityLow;
		}
		double negInertiaPower = negInertia * negInertiaScalar;
		negInertiaAccumulator += negInertiaPower;

		wheel = wheel + negInertiaAccumulator;
		if (negInertiaAccumulator > 1) {
			negInertiaAccumulator -= 1;
		} else if (negInertiaAccumulator < -1) {
			negInertiaAccumulator += 1;
		} else {
			negInertiaAccumulator = 0;
		}
		linearPower = throttle;

		// Quickturn!
		if (isQuickTurn) {
			if (Math.abs(linearPower) < 0.2) {
				double alpha = 0.1;
				quickStopAccumulator = (1 - alpha) * quickStopAccumulator + alpha * Values.limit(-1.0, wheel, 1.0) * 5;
			}
			overPower = 1.0;
			if (isHighGear) {
				sensitivity = 1.0;
			} else {
				sensitivity = 1.0;
			}
			angularPower = wheel;
		} else {
			overPower = 0.0;
			angularPower = Math.abs(throttle) * wheel * sensitivity - quickStopAccumulator;
			if (quickStopAccumulator > 1) {
				quickStopAccumulator -= 1;
			} else if (quickStopAccumulator < -1) {
				quickStopAccumulator += 1;
			} else {
				quickStopAccumulator = 0.0;
			}
		}

		SmartDashboard.putBoolean("quickTurn", isQuickTurn);
		SmartDashboard.putNumber("wheel", wheel);
		SmartDashboard.putNumber("angularPower", angularPower);

		rightSpeed = leftSpeed = linearPower;
		leftSpeed += angularPower;
		rightSpeed -= angularPower;

		if (leftSpeed > 1.0) {
			rightSpeed -= overPower * (leftSpeed - 1.0);
			leftSpeed = 1.0;
		} else if (rightSpeed > 1.0) {
			leftSpeed -= overPower * (rightSpeed - 1.0);
			rightSpeed = 1.0;
		} else if (leftSpeed < -1.0) {
			rightSpeed += overPower * (-1.0 - leftSpeed);
			leftSpeed = -1.0;
		} else if (rightSpeed < -1.0) {
			leftSpeed += overPower * (-1.0 - rightSpeed);
			rightSpeed = -1.0;
		}
		drivetrain.tank(rightSpeed, leftSpeed);
	}

	public double handleDeadband(double val, double deadband) {
		return Math.abs(val) > Math.abs(deadband) ? val : 0.0;
	}
}