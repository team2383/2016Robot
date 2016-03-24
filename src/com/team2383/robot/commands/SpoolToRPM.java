package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		SmartDashboard.putBoolean("spooling?", false);
		this.rpmSupplier = rpmSupplier;
	}

	public SpoolToRPM(DoubleSupplier rpmSupplier) {
		super("Spool To RPM");
		requires(shooterFlywheel);
		SmartDashboard.putBoolean("spooling?", false);
		this.rpmSupplier = rpmSupplier;
	}

	public SpoolToRPM() {
		super("Spool To RPM");
		requires(shooterFlywheel);
		SmartDashboard.putBoolean("spooling?", false);
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
		SmartDashboard.putBoolean("spooling?", true);
		shooterFlywheel.spoolToSetpoint();
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		SmartDashboard.putBoolean("spooling?", false);
		shooterFlywheel.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
