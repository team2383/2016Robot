package com.team2383.robot.commands;

import static com.team2383.robot.HAL.feeder;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Activates the feeder wheels in the intake direction to push the ball into the
 * spooled up shooter wheels
 *
 * @author Matthew Alonso
 *
 */
public class Shoot extends Command {
	public Shoot() {
		super("Kick Ball");
		requires(feeder);
	}

	public Shoot(double timeout) {
		super("Kick Ball", timeout);
		requires(feeder);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		feeder.feedIn();
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		feeder.stop();
	}

	@Override
	protected void interrupted() {
		feeder.stop();
	}

}
