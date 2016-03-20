package com.team2383.robot.commands;

import com.team2383.robot.HAL;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class ActuateHoodStop extends Command {
	private final Value value;

	public ActuateHoodStop(boolean value) {
		super("Actuate Hood Stop");
		this.value = value ? Value.kForward : Value.kReverse;
	}

	@Override
	protected void initialize() {
		HAL.hoodHardStop.set(this.value);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		// hold forward until command is canceled
		return true;
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
