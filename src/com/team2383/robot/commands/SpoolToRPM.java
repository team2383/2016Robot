package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Spool shooter flywheel to maximum power
 */
public class SpoolToRPM extends Command {

	private final double rpm;

	public SpoolToRPM(double rpm) {
		super("Spool To RPM");
		requires(shooterFlywheel);
		this.rpm = rpm;
	}

	public SpoolToRPM(double rpm, double timeout) {
		super("Spool To RPM", timeout);
		requires(shooterFlywheel);
		this.rpm = rpm;
	}

	@Override
	protected void initialize() {
		shooterFlywheel.setSetpoint(rpm);
	}

	@Override
	protected void execute() {
		shooterFlywheel.spoolToSetpoint();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		shooterFlywheel.stop();
	}

	@Override
	protected void interrupted() {
		shooterFlywheel.stop();
	}

}
