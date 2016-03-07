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
         * @param hoodAngle angle of hood
         * @param shooterRPM shooter RPM to maintain
         */
        public ShooterPreset(double hoodAngle, double shooterRPM) {
            this.hoodAngle = hoodAngle;
            this.shooterRPM = shooterRPM;
        }
    }

    public static enum Preset {
        closeHoodAndStopShooter(new ShooterPreset(0.0, 0)),
        feed(new ShooterPreset(5.0, 0)),
        batter(new ShooterPreset(29.92, 3000)),
        courtyardClose(new ShooterPreset(54.73, 4850)),
        courtyardFar(new ShooterPreset(50, 4850));

        private final ShooterPreset preset;

        Preset(ShooterPreset preset) {
            this.preset = preset;
        }

        public ShooterPreset get() {
            return preset;
        }
    }

    public static int shooterRpmTolerance = 100;
    public static double shooterFollowThruTime = 3.0;
    public static double shooterFeederKickPower = 1.0;

    public static int hoodDegreeTolerance = 1;
    public static double hoodStallCurrent = 8; // amps
    public static double hoodReverseLimit = 0.630;
    public static double hoodForwardLimit = 1.000;

    public static double feedCurrentMultiplier = 2.0;
    public static double feedCurrentMinimum = 5;
    public static double feedPower = 1.0;
    public static double feedShooterPower = -0.1;

    public static double feedPushAwayPower = -0.2;
    public static double feedPushAwayLengthInSeconds = 0.7;

    public static double driveWheelDiameter = 6.0;
    public static double driveWheelCircumference = driveWheelDiameter * Math.PI;
    public static double driveFeetPerDegree = driveWheelCircumference / 360.0;
    public static double driveUpshiftFpsThreshold = 4.0;
    public static double driveDownshiftFpsThreshold = 3.0;

    public static double inputExpo = 0.5;
    public static double inputDeadband = 0.1;
    public static double hoodP = 2.3;
    public static double hoodI = 0.0008;
    public static double hoodD = 25;
}