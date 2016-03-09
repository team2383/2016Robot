package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Spool shooter flywheel to maximum power
 */
public class Spool extends Command {

	public Spool() {
		super("Spool");
		requires(shooterFlywheel);
	}

	public Spool(double timeout) {
		super("Spool", timeout);
		requires(shooterFlywheel);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		shooterFlywheel.spool();
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
