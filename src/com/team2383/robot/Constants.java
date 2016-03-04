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
        feeding(new ShooterPreset(2.0, 0)),
        batter(new ShooterPreset(12.06, 4000)),
        courtyardClose(new ShooterPreset(19.26, 4700)),
        courtyardFar(new ShooterPreset(20.25, 4850));

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
    public static double hoodAngleMin = 1.205;
    public static double hoodAngleMax = 0.705;

    public static double feedCurrentMultiplier = 6.0;
    public static double feedPower = 1.0;
    public static double feedShooterPower = -0.1;

    public static double feedPushAwayPower = -0.2;
    public static double feedPushAwayLengthInSeconds = 0.3;

    public static double driveWheelDiameter = 6.0;
    public static double driveWheelCircumference = driveWheelDiameter * Math.PI;
    public static double driveFeetPerDegree = driveWheelCircumference / 360.0;
    public static double driveUpshiftFpsThreshold = 4.0;
    public static double driveDownshiftFpsThreshold = 3.0;

    public static double inputExpo = 0.5;
    public static double inputDeadband = 0.1;
}