package com.team2383.robot.commands;

import com.team2383.robot.HAL;

import edu.wpi.first.wpilibj.command.Command;

public class CameraSwitcher extends Command {
	public CameraSwitcher() {
		requires(HAL.dualCams);
	}

	@Override
	protected void initialize() {
		HAL.dualCams.switchCam();
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
