package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterHood;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves arms until canceled or times out
 *
 * @author Matthew Alonso
 *
 */

public class MoveHood extends Command {
	private final DoubleSupplier hoodPower;

	public MoveHood(DoubleSupplier hoodPower) {
		super("Move Hood");
		requires(shooterHood);
		this.hoodPower = hoodPower;
	}

	public MoveHood(DoubleSupplier hoodPower, double timeout) {
		super("Move Hood", timeout);
		requires(shooterHood);
		this.hoodPower = hoodPower;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		shooterHood.moveAtSpeed(hoodPower.getAsDouble());
	}

	@Override
	protected boolean isFinished() {
		return false; //this.isTimedOut();
	}

	@Override
	protected void end() {
		shooterHood.stop();
	}

	@Override
	protected void interrupted() {
		shooterHood.stop();
	}

}
