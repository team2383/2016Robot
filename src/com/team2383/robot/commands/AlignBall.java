package com.team2383.robot.commands;

import static com.team2383.robot.HAL.shooterFlywheel;

public class AlignBall extends SpoolToRPM {
	private double before = 0.0;

	public AlignBall() {
		super(-2000);
	}

	@Override
	public void initialize() {
		before = shooterFlywheel.getSuppliedSetpoint();
		super.initialize();
	}

	@Override
	public void end() {
		shooterFlywheel.setRPM(before);
		super.end();
	}

	@Override
	public void interrupted() {
		shooterFlywheel.setRPM(before);
		super.interrupted();
	}
}
