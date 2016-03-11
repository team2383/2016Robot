package com.team2383.robot.commands;

import com.team2383.robot.HAL;

import edu.wpi.first.wpilibj.command.Command;

public class CameraStreamer extends Command {
	public CameraStreamer() {
		requires(HAL.dualCams);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		HAL.dualCams.updateCam();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
