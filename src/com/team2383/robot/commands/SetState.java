package com.team2383.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Moves arms until canceled or times out
 *
 * @author Matthew Alonso
 *
 */

public class SetState<StateT extends Enum<StateT>> extends Command {

	private final StatefulSubsystem<StateT> subsystem;
	private final StateT state;
	private final StateT endState;

	public static abstract class StatefulSubsystem<T> extends Subsystem {
		public abstract void setState(T state);
	}

	public SetState(StatefulSubsystem<StateT> subsystem, StateT state) {
		this(subsystem, state, null);
	}

	public SetState(StatefulSubsystem<StateT> subsystem, StateT state, StateT endState) {
		super("State Setter");
		requires(subsystem);
		this.subsystem = subsystem;
		this.state = state;
		this.endState = endState;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		subsystem.setState(state);
	}

	@Override
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		subsystem.setState(endState);
	}

	@Override
	protected void interrupted() {
		subsystem.setState(endState);
	}

}
