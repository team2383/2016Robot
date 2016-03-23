package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

public class SetSpoolRPM extends Command {
	private final DoubleSupplier rpmSupplier;

	public SetSpoolRPM(DoubleSupplier rpmSupplier) {
		super("Set Spool RPM");
		// requires(shooterFlywheel) commented out bc you want to be able to
		// adjust rpm without stopping shooter
		this.rpmSupplier = rpmSupplier;
	}

	@Override
	protected void initialize() {
		shooterFlywheel.setRPM(rpmSupplier);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
