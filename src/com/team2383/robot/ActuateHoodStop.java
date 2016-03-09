package com.team2383.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class ActuateHoodStop extends Command {
	private final DoubleSolenoid hoodStop;

	public ActuateHoodStop(DoubleSolenoid hoodStop) {
		super("Actuate Hood Stop");
		this.hoodStop = hoodStop;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		hoodStop.set(Value.kForward);
	}

	@Override
	protected boolean isFinished() {
		// hold forward until command is canceled
		return false;
	}

	@Override
	protected void end() {
		hoodStop.set(Value.kReverse);
	}

	@Override
	protected void interrupted() {
		hoodStop.set(Value.kReverse);
	}

}
