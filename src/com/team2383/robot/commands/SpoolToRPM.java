package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Spool shooter flywheel to maximum power
 */
public class SpoolToRPM extends Command {

	private final DoubleSupplier rpmSupplier;

	public SpoolToRPM(double rpm) {
		this(() -> rpm);
	}

	public SpoolToRPM(double rpm, double timeout) {
		this(() -> rpm, timeout);
	}

	public SpoolToRPM(DoubleSupplier rpmSupplier) {
		super("Spool To RPM");
		requires(shooterFlywheel);
		this.rpmSupplier = rpmSupplier;
	}

	public SpoolToRPM(DoubleSupplier rpmSupplier, double timeout) {
		super("Spool To RPM", timeout);
		requires(shooterFlywheel);
		this.rpmSupplier = rpmSupplier;
	}

	@Override
	protected void initialize() {
		shooterFlywheel.spoolToSetpoint(rpmSupplier.getAsDouble());
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
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
