package com.team2383.robot.commands;

import static com.team2383.robot.HAL.vision;

import edu.wpi.first.wpilibj.command.Command;

public class UpdateVision extends Command {
	public UpdateVision() {
		super("UpdateVisionSlow");
		requires(vision);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		vision.update();
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
