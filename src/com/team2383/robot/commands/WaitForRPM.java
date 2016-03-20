package com.team2383.robot.commands;

import com.team2383.robot.HAL;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForRPM extends Command {

	private double timeAtSetpoint;
	private double lastCheck;
	private final double wait;

	public WaitForRPM(double wait) {
		super("WaitForRPM");
		this.wait = wait;
	}

	public WaitForRPM(double wait, double timeout) {
		super("WaitForRPM", timeout);
		this.wait = wait;
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		if (HAL.shooterFlywheel.isAtSetpoint()) {
			timeAtSetpoint += this.timeSinceInitialized() - lastCheck;
		} else {
			timeAtSetpoint = 0;
		}
		lastCheck = this.timeSinceInitialized();
		return timeAtSetpoint >= wait || this.isTimedOut();
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
