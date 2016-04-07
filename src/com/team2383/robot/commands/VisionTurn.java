package com.team2383.robot.commands;

import static com.team2383.robot.HAL.vision;

import com.team2383.robot.Constants;

public class VisionTurn extends GyroTurn {
	private double timeWithoutTarget;
	private final boolean finish;

	public VisionTurn(boolean finish) {
		super(0);
		this.finish = finish;
	}

	public VisionTurn() {
		super(0);
		this.finish = true;
	}

	@Override
	protected void initialize() {
		super.initialize();
		cancelIfNoTarget();
		// negative! vision azimuths and gyro angles are opposites!
		this.setSetpoint(vision.update().getNearestTarget().getAzimuth());
	}

	@Override
	public boolean isFinished() {
		return finish && super.isFinished();
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
