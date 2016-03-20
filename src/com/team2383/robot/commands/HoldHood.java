package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterHood;

import edu.wpi.first.wpilibj.command.Command;

public class HoldHood extends Command {

	public HoldHood() {
		requires(shooterHood);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		// shooterHood.holdPosition();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
