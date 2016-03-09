package com.team2383.robot.commands;

import static com.team2383.robot.HAL.arms;

import com.team2383.robot.subsystems.Arms.State;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves arms until canceled or times out
 *
 * @author Matthew Alonso
 *
 */

public class MoveArms extends Command {
	private final State state;

	public MoveArms(State state) {
		super("Move Arms");
		requires(arms);
		this.state = state;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		arms.setState(state);
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		arms.setState(State.STOPPED);
	}

	@Override
	protected void interrupted() {
		arms.setState(State.STOPPED);
	}

}
