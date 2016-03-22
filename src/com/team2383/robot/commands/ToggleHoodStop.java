package com.team2383.robot.commands;

import com.team2383.robot.HAL;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleHoodStop extends Command {
	public ToggleHoodStop() {
		super("Toggle Hood Stop");
	}

	@Override
	protected void initialize() {
		HAL.hoodHardStop.set(Value.kForward);
	}

	@Override
	protected void execute() {
		HAL.hoodHardStop.set(Value.kForward);
	}

	@Override
	protected boolean isFinished() {
		// hold forward until command is canceled
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		HAL.hoodHardStop.set(Value.kReverse);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		this.end();
	}
}
