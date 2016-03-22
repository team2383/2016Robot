package com.team2383.robot.commands;

import static com.team2383.robot.HAL.drivetrain;

import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftTo extends Command {
	private final Gear gear;

	public ShiftTo(Gear gear) {
		super("ShiftTo");
		this.gear = gear;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		drivetrain.shiftTo(gear);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		drivetrain.shiftTo(gear);
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
