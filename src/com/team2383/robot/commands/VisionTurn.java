package com.team2383.robot.commands;

import static com.team2383.robot.HAL.vision;

import com.team2383.robot.Constants;

public class VisionTurn extends GyroTurn {
	private double timeWithoutTarget;

	public VisionTurn() {
		super(0);
	}

	@Override
	protected void initialize() {
		super.initialize();
		// negative! vision azimuths and gyro angles are opposites!
		this.setSetpoint(-vision.update().getNearestTarget().getAzimuth());
	}

	@Override
	protected void execute() {
		cancelIfNoTarget();
		super.execute();
	}

	private void cancelIfNoTarget() {
		if (vision.getNearestTarget().getDistance() == 0.0) {
			timeWithoutTarget += this.timeSinceInitialized() - timeWithoutTarget;
			if (timeWithoutTarget >= Constants.visionAlignOffset) {
				if (getGroup() != null) {
					getGroup().cancel();
				} else {
					cancel();
				}
			}
		}
	}

}
