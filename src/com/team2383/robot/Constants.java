package com.team2383.robot;

import java.util.LinkedList;

public class Constants {
	public static class ShooterPreset {
		public static LinkedList<ShooterPreset> presets = new LinkedList<ShooterPreset>();
		public int currentPresetIndex;
		public final double hoodAngle;
		public final double shooterRPM;

		/**
		 * Preset for shooting at position
		 *
		 * @param hoodAngle
		 *            angle of hood
		 * @param shooterRPM
		 *            shooter RPM to maintain
		 */
		public ShooterPreset(double hoodAngle, double shooterRPM) {
			this.hoodAngle = hoodAngle;
			this.shooterRPM = shooterRPM;
		}
	}

	public static enum Preset {
		closeHoodAndStopShooter(new ShooterPreset(0.0, 0)), feed(new ShooterPreset(5.0, 0)), batter(
				new ShooterPreset(29.92, 3000)), courtyardClose(new ShooterPreset(54.73, 4850)), courtyardFar(
						new ShooterPreset(50, 4850));

		private final ShooterPreset preset;

		Preset(ShooterPreset preset) {
			this.preset = preset;
		}

		public ShooterPreset get() {
			return preset;
		}
	}

	public static double shooterMaxRPM = 4500;
	public static double shooterMinRPM = 2500;
	public static int shooterRPMTolerance = 100;
	public static double shooterFollowThruTime = 3.0;
	public static double shooterFeederKickPower = 1.0;

	public static double shooterFlywheelP = 0.35;
	public static double shooterFlywheelI = 0.0035;
	public static double shooterFlywheelD = 0.0;
	public static double shooterFlywheelF = 0.030;
	public static int shooterFlywheelIZone = 40;

	public static int hoodDegreeTolerance = 1;
	public static double hoodStallCurrent = 8; // amps
	public static double hoodReverseLimit = -0.564;
	public static double hoodForwardLimit = -0.982;

	public static double hoodPositionP = 2.3;
	public static double hoodPositionI = 0.0023;
	public static double hoodPositionD = 0.5;
	public static double hoodPositionF = 0.002;
	public static int hoodPositionIZone = 40;

	public static double feedCurrentMultiplier = 2.0;
	public static double feedCurrentMinimum = 5;
	public static double feedPower = 1.0;
	public static double feedShooterPower = -0.1;

	public static double feedPushAwayPower = -0.2;
	public static double feedPushAwayLengthInSeconds = 0.7;

	public static double driveWheelDiameter = 7.15;
	public static double driveWheelCircumference = driveWheelDiameter * Math.PI;
	public static double driveInchesPerDegree = driveWheelCircumference / 360.0;
	public static double driveFeetPerDegree = driveInchesPerDegree / 12.0;
	public static double driveUpshiftFPSThreshold = 4.0;
	public static double driveDownshiftFPSThreshold = 3.0;

	public static double driveHeadingMoveToTolerance = 0.85;
	public static double driveHeadingMoveToP = 0.023;
	public static double driveHeadingMoveToI = 0.0023;
	public static double driveHeadingMoveToD = 0.0;
	public static double driveHeadingMoveToVelocity = 0.8;

	public static double driveHeadingMaintainTolerance = 0.25;
	public static double driveHeadingMaintainP = 0.18;
	public static double driveHeadingMaintainI = 0.0001;
	public static double driveHeadingMaintainD = 0.0;
	public static double driveHeadingMaintainF = 0;

	public static double drivePositionTolerance = 0.85;
	public static double drivePositionP = 0.06;
	public static double drivePositionI = 0.0020;
	public static double drivePositionD = 0.0;
	public static double drivePositionF = 0;

	public static double inputExpo = 0.5;
	public static double inputDeadband = 0.1;

	public static boolean useMechanicalHoodPresets = true;

	public static double navXResetDelay = 0.07; // seconds
	public static double visionTargetAzimuthThreshold = 0.7;
}