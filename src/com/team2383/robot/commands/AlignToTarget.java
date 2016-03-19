package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.vision;

import com.team2383.robot.subsystems.Vision.Target;

import edu.wpi.first.wpilibj.command.Command;

public class AlignToTarget extends Command {
	private Target startTarget;

	public AlignToTarget() {
		requires(vision);
		requires(drivetrain);
	}

	@Override
	protected void initialize() {
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
