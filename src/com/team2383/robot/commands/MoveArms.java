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
	private final double speed;

	public MoveArms(State state) {
		super("Move Arms");
		requires(arms);
		this.speed = state.getSpeed();
	}

	public MoveArms(double speed) {
		super("Move Arms");
		requires(arms);
		this.speed = speed;
	}

	public MoveArms(State state, double timeout) {
		super("Move Arms", timeout);
		requires(arms);
		this.speed = state.getSpeed();
		;
	}

	public MoveArms(double speed, double timeout) {
		super("Move Arms", timeout);
		requires(arms);
		this.speed = speed;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		arms.set(speed);
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
