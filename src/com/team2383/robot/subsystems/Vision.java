package com.team2383.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Vision extends Subsystem {

	private double distanceFromGoal;
	private double angle;

	private void hasNewData(double distanceFromGoal, double angle) {
		this.distanceFromGoal = distanceFromGoal;
		this.angle = angle;
	}

	@Override
	protected void initDefaultCommand() {
		new VisionDataSocket(this::hasNewData);
	}

	public double getAngle() {
		return angle;
	}

	public double getDistanceFromGoal() {
		return distanceFromGoal;
	}
}
