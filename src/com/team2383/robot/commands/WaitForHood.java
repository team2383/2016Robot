package com.team2383.robot.commands;

import com.team2383.robot.Constants;
import com.team2383.robot.HAL;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForHood extends Command {

	private double timeAtSetpoint;
	private double lastCheck;
	private final double wait;

	public WaitForHood() {
		super("WaitForRPM");
		this.wait = Constants.pidSetpointWait;
	}

	public WaitForHood(double wait) {
		super("WaitForRPM");
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
		if (HAL.shooterHood.isAtSetpoint()) {
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
