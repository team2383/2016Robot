package com.team2383.robot;

import java.util.LinkedList;

public class Constants {
	public static class ShooterPreset {
		public static LinkedList<ShooterPreset> presets = new LinkedList<ShooterPreset>();
		public int currentPresetIndex;
		public final double hoodRotations;
		public final double shooterRPM;

		/**
		 * Preset for shooting at position
		 *
		 * @param hoodRotation
		 *            angle of hood
		 * @param shooterRPM
		 *            shooter RPM to maintain
		 */
		public ShooterPreset(double hoodRotation, double shooterRPM) {
			this.hoodRotations = hoodRotation;
			this.shooterRPM = shooterRPM;
		}
	}

	public static enum Preset {
		closed(new ShooterPreset(0.0, 0)), tower(new ShooterPreset(0.049, 3700)), onBatter(
				new ShooterPreset(0.131, 3850)), courtyardMid(new ShooterPreset(0.166, 3850)), courtyardFar(
						new ShooterPreset(0.182, 4050));

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
	public static double shooterFollowThruTime = 0.6;
	public static double shooterFeederKickPower = 1.0;

	public static double shooterFlywheelP = 0.41;
	public static double shooterFlywheelI = 0.008;
	public static double shooterFlywheelD = 0.0;
	public static double shooterFlywheelF = 0.0325;
	public static int shooterFlywheelIZone = 40;

	public static double hoodRotationTolerance = 1.5 / 360.0;
	public static double hoodReverseLimit = 0.550;
	public static double hoodForwardLimit = 0.990;

	public static double hoodPositionP = 2;
	public static double hoodPositionI = 0.01;
	public static double hoodPositionD = 0.0;
	public static double hoodPositionF = 0;
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

	public static double driveHeadingMoveToTolerance = 1.0;
	public static double driveHeadingMoveToP = 0.075;
	public static double driveHeadingMoveToI = 0.0034; // .003 at ROC
	public static double driveHeadingMoveToD = 0.025; // 0 at ROC
	public static double driveHeadingMoveToVelocity = 0.8;

	public static double driveTeleopHeadingMaintainTolerance = 0.25;
	public static double driveTeleopHeadingMaintainP = 0.10;
	public static double driveTeleopHeadingMaintainI = 0.0001;
	public static double driveTeleopHeadingMaintainD = 0.0;
	public static double driveTeleopHeadingMaintainF = 0;

	public static double driveHeadingMaintainTolerance = 0.25;
	public static double driveHeadingMaintainP = 0.17;
	public static double driveHeadingMaintainI = 0.0001;
	public static double driveHeadingMaintainD = 0.0;
	public static double driveHeadingMaintainF = 0;

	public static double drivePositionTolerance = 1.2;
	public static double drivePositionP = 0.3;
	public static double drivePositionI = 0.0;
	public static double drivePositionD = 0.25;
	public static double drivePositionF = 0;

	public static double driveHoldPositionP = 2.3;
	public static double driveHoldPositionI = 0.0023;
	public static double driveHoldPositionD = 0.0;
	public static double driveHoldPositionF = 0;
	public static int driveHoldPositionIZone = 50;

	public static double inputExpo = 0.32;
	public static double inputDeadband = 0.05;

	public static double navXResetDelay = 0.07; // seconds

	public static double visionTargetAzimuthThreshold = 0.4;
	public static double visionAlignOffset = -2.9; // -2.7 at last Practice

	public static double visionTurnP = 0.053; // 0.033 at ROC
	public static double visionTurnI = 0.0013; // .0017 at ROC
	public static double visionTurnD = 0.54; // 0

	public static double pidSetpointWait = 0.15;
	public static double shooterRPMWaitTime = 0.10;
	public static double visionTimeout = 1.0;
}