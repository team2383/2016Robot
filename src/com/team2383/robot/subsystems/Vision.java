package com.team2383.robot.subsystems;

import java.util.ArrayList;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.UpdateVision;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends Subsystem {
	private final NetworkTable visionTable;

	public class LookupTable extends ArrayList<LookupTable.Entry> {
		/**
		 *
		 */
		private static final long serialVersionUID = -8266344803439288169L;

		public class Entry {
			private final double distance;
			private final double hoodRotations;
			private final double flywheelRPM;

			public Entry(double distance, double hoodRotations, double flywheelRPM) {
				this.distance = distance;
				this.hoodRotations = hoodRotations;
				this.flywheelRPM = flywheelRPM;
			}

			public double getDistance() {
				return distance;
			}

			public double getHoodRotations() {
				return hoodRotations;
			}

			public double getFlywheelRPM() {
				return flywheelRPM;
			}

			@Override
			public String toString() {
				return "lte[" + this.distance + "," + this.hoodRotations + "," + this.flywheelRPM + "]";
			}
		}

		public void add(double distance, double hoodRotations, double flywheelRPM) {
			this.add(new Entry(distance, hoodRotations, flywheelRPM));
		}

		public Entry getEntryNear(double distance) {
			Entry near = get(0);
			double diff = Math.abs(near.distance - distance);
			for (Entry e : this) {
				if (Math.abs(e.distance - distance) < diff) {
					near = e;
					diff = Math.abs(e.distance - distance);
				}
			}
			return near;
		}

		public Entry getEntryAvg(double distance) {
			Entry upper = null;
			Entry lower = null;
			for (Entry e : this) {
				if (e.distance > distance) {
					upper = e;
					lower = get(indexOf(upper) - 1);
				}
			}
			if (upper == null) {
				upper = get(size() - 1);
				lower = get(size() - 1);
			}
			;
			return new Entry(distance, (upper.getHoodRotations() + lower.getHoodRotations()) / 2.0,
					(upper.getFlywheelRPM() + lower.getFlywheelRPM()) / 2.0);
		}
	}

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

		public double getRawAzimuth() {
			return azimuth;
		}

		public double getAzimuth() {
			return azimuth + Constants.visionAlignOffset;
		}

		public boolean isAligned() {
			return Math.abs(azimuth + Constants.visionAlignOffset) < Constants.visionTargetAzimuthThreshold;
		}
	}

	private final LookupTable lookupTable = new LookupTable();

	public Vision() {
		visionTable = NetworkTable.getTable("vision");

		lookupTable.add(86.460, 0.109, 3700);
		lookupTable.add(89.662, 0.119, 3700);
		lookupTable.add(93.832, 0.119, 3700);
		lookupTable.add(100.036, 0.130, 3700);
		lookupTable.add(109.048, 0.148, 3700);
		lookupTable.add(115.280, 0.156, 3700);
		lookupTable.add(122.266, 0.163, 3700);
		lookupTable.add(131.569, 0.171, 3700);
		lookupTable.add(137.55, 0.179, 3700);
		lookupTable.add(144.100, 0.179, 3700);
		lookupTable.add(147.417, 0.179, 3700);
		lookupTable.add(155.184, 0.186, 3700);

		/*
		 * Soflo table lookupTable.add(96.0, 0.129, 3900); lookupTable.add(98.0,
		 * 0.135, 3900); lookupTable.add(99.0, 0.135, 3900);
		 * lookupTable.add(111.04, 0.143, 3900); lookupTable.add(117.5, 0.154,
		 * 3900); lookupTable.add(124.7, 0.155, 3900); lookupTable.add(130.15,
		 * 0.166, 3900); lookupTable.add(140.7, 0.166, 3900);
		 * lookupTable.add(147.6, 0.166, 3900); lookupTable.add(151.3, 0.163,
		 * 3900); lookupTable.add(153.2, 0.163, 3900);
		 */

		/*
		 * Vish's table
		 *
		 * lookupTable.add(96.0, 0.112, 3900); lookupTable.add(98.0, 0.116,
		 * 3900); lookupTable.add(99.0, 0.118, 3900); lookupTable.add(117.5,
		 * 0.143, 3900); lookupTable.add(124.7, 0.15, 3900);
		 * lookupTable.add(130.15, 0.55, 3900); lookupTable.add(140.7, 0.163,
		 * 3900); lookupTable.add(147.6, 0.1676, 3900); lookupTable.add(151.3,
		 * 0.17, 3900); lookupTable.add(153.2, 0.17, 3900);
		 */
	}

	private ArrayList<Target> targets = new ArrayList<>();

	private void hasNewData(ArrayList<Target> targets) {
		this.targets = targets;
	}

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new UpdateVision());
	}

	public ArrayList<Target> getTargets() {
		return targets;
	}

	public boolean hasTarget() {
		return getNearestTarget().getDistance() != 0;
	}

	public Vision update() {
		ArrayList<Vision.Target> targets = new ArrayList<>();
		double[] distances = {};
		double[] azimuths = {};
		distances = visionTable.getNumberArray("distances", distances);
		azimuths = visionTable.getNumberArray("azimuths", azimuths);

		if (distances.length != azimuths.length)
			return this;

		for (int i = 0; i < distances.length; i++) {
			targets.add(new Target(distances[i], azimuths[i]));
		}

		targets.sort((a, b) -> {
			return (int) Math.abs(a.getAzimuth()) - (int) Math.abs(b.getAzimuth());
		});

		hasNewData(targets);
		return this;
	}

	public LookupTable.Entry getNearestEntryForTarget(Target t) {
		return lookupTable.getEntryNear(t.distance);
	}

	public LookupTable.Entry getAvgEntryForTarget(Target t) {
		return lookupTable.getEntryAvg(t.distance);
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
