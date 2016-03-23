package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Spool shooter flywheel
 */
public class SpoolToRPM extends Command {
	private final DoubleSupplier rpmSupplier;

	public SpoolToRPM(double rpm, double timeout) {
		this(() -> rpm, timeout);
	}

	public SpoolToRPM(double rpm) {
		this(() -> rpm);
	}

	public SpoolToRPM(DoubleSupplier rpmSupplier, double timeout) {
		super("Spool To RPM", timeout);
		requires(shooterFlywheel);
		this.rpmSupplier = rpmSupplier;
	}

	public SpoolToRPM(DoubleSupplier rpmSupplier) {
		super("Spool To RPM");
		requires(shooterFlywheel);
		this.rpmSupplier = rpmSupplier;
	}

	public SpoolToRPM() {
		super("Spool To RPM");
		requires(shooterFlywheel);
		this.rpmSupplier = null;
	}

	@Override
	protected void initialize() {
		if (rpmSupplier != null) {
			shooterFlywheel.setRPM(rpmSupplier.getAsDouble());
		}
	}

	@Override
	protected void execute() {
		shooterFlywheel.spoolToSetpoint();
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
