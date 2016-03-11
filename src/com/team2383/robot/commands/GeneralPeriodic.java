package com.team2383.robot.commands;

import com.team2383.robot.HAL;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Runs general periodic tasks, like updating dashboard, fetching vision data,
 * and updating setpoints not handled by dedicated commands (shooter RPM);
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

		// Drivetrain
		SmartDashboard.putNumber("Drive Encoder Rotations", HAL.drivetrain.getRotations());
		SmartDashboard.putNumber("Drive Encoder Inches", HAL.drivetrain.getInches());
		SmartDashboard.putString("Drivetrain Gear", HAL.drivetrain.getGear().toString());

		// Shooter Flywheel
		SmartDashboard.putNumber("Shooter Flywheel Target RPM", HAL.shooterFlywheel.getSetpoint());
		HAL.shooterFlywheel.setSetpoint(SmartDashboard.getNumber("Shooter Flywheel Target RPM"));
		SmartDashboard.putNumber("Shooter Flywheel Current RPM", HAL.shooterFlywheel.getRPM());
		SmartDashboard.putBoolean("Shooter At Setpoint", HAL.shooterFlywheel.isAtSetpoint());

		// Shooter Hood
		SmartDashboard.putNumber("Hood Current Rotations", HAL.shooterHood.getRotations());
		SmartDashboard.putNumber("Hood Target Rotations", HAL.shooterHood.getSetpoint());
		SmartDashboard.putBoolean("Hood At Setpoint", HAL.shooterHood.isAtSetpoint());

		// Pneumatics
		SmartDashboard.putBoolean("Hard Stop Extended?", HAL.hoodTopLimit.get() == Value.kForward ? true : false);
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
