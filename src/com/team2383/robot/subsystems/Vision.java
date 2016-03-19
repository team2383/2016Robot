package com.team2383.robot.subsystems;

import java.util.ArrayList;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.UpdateVision;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Vision extends Subsystem {

	public static class Target {
		private final double distance;
		private final double azimuth;

		public Target(double distance, double azimuth) {
			this.distance = distance;
			this.azimuth = azimuth;
		}

		public double getDistance() {
			return distance;
		}

		public double getAzimuth() {
			return azimuth;
		}

		public boolean isAligned() {
			return Math.abs(azimuth) < Constants.visionTargetAzimuthThreshold;
		}
	}

	private ArrayList<Target> targets = new ArrayList<>();

	private void hasNewData(ArrayList<Target> targets) {
		this.targets = targets;
	}

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new UpdateVision(this::hasNewData));
	}

	public ArrayList<Target> getTargets() {
		return targets;
	}

	public Target getNearestTarget() {
		try {
			Target t = targets.get(0);
			return t == null ? new Target(0, 0) : t;
		} catch (IndexOutOfBoundsException e) {
			return new Target(0, 0);
		}
	}

	public void setTargets(ArrayList<Target> targets) {
		this.targets = targets;
	}
}
