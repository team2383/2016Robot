package com.team2383.robot.commands;

import com.team2383.robot.HAL;
import com.team2383.robot.OI;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Runs general periodic tasks, like updating dashboard, and updating setpoints
 * not handled by dedicated commands (shooter RPM);
 *
 * @author Matthew Alonso
 *
 */
public class GeneralPeriodic extends Command {

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		// General
		SmartDashboard.putNumber("Gyro Yaw", HAL.navX.getYaw());
		SmartDashboard.putNumber("Total Robot Current Draw", HAL.PDP.getTotalCurrent());

		// Vision
		SmartDashboard.putNumber("Closest target distance", HAL.vision.getNearestTarget().getDistance());
		SmartDashboard.putNumber("Closest target azimuth", HAL.vision.getNearestTarget().getAzimuth());
		SmartDashboard.putNumber("Closest target raw azimuth", HAL.vision.getNearestTarget().getAzimuth());
		SmartDashboard.putBoolean("Has target", HAL.vision.hasTarget());
		SmartDashboard.putBoolean("Aligned With closest target", HAL.vision.getNearestTarget().isAligned());

		// Drivetrain
		SmartDashboard.putNumber("Drive Encoder Rotations", HAL.drivetrain.getRotations());
		SmartDashboard.putNumber("Drive Encoder Inches", HAL.drivetrain.getInches());
		SmartDashboard.putString("Drivetrain Gear", HAL.drivetrain.getGear().toString());

		// Shooter Flywheel
		SmartDashboard.putNumber("Shooter Joystick Choosen RPM", OI.shooterSpeed.getAsDouble());
		SmartDashboard.putNumber("Shooter Flywheel Target RPM", HAL.shooterFlywheel.getSetpoint());
		SmartDashboard.putNumber("Shooter Flywheel Current RPM", HAL.shooterFlywheel.getRPM());
		SmartDashboard.putBoolean("Shooter At Setpoint", HAL.shooterFlywheel.isAtSetpoint());

		// Shooter Hood
		SmartDashboard.putNumber("Hood Current Raw Rotations", HAL.shooterHood.getRawRotations());
		SmartDashboard.putNumber("Hood Setpoint Raw Rotations", HAL.shooterHood.getRawSetpoint());
		SmartDashboard.putNumber("Hood Current Rotations", HAL.shooterHood.getRotations());
		SmartDashboard.putNumber("Hood Setpoint Rotations", HAL.shooterHood.getSetpoint());
		SmartDashboard.putBoolean("Hood At Setpoint", HAL.shooterHood.isAtSetpoint());

		// Pneumatics
		SmartDashboard.putBoolean("Hard Stop Extended?", HAL.hoodHardStop.get() == Value.kForward ? true : false);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
