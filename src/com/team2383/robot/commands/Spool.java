package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;
import static com.team2383.robot.HAL.shooterHood;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Spool shooter flywheel to maximum power
 */
public class Spool extends Command {

	public Spool() {
		super("Spool");
		requires(shooterFlywheel);
		requires(shooterHood);
	}

	public Spool(double timeout) {
		super("Spool", timeout);
		requires(shooterHood);
		requires(shooterFlywheel);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		shooterHood.moveAtSpeed(0.25);
		shooterFlywheel.spool();
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		shooterHood.stop();
		shooterFlywheel.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
