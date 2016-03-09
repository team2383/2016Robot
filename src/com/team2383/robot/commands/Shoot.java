package com.team2383.robot.commands;

import static com.team2383.robot.HAL.feeder;

import com.team2383.robot.subsystems.Feeder.State;

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

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		feeder.setState(State.FEEDING);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		feeder.setState(State.STOPPED);
	}

	@Override
	protected void interrupted() {
		feeder.setState(State.STOPPED);
	}

}
