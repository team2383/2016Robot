/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Solenoid.Direction;
import org.strongback.components.TalonSRX;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;

public class Config {
    /** Joysticks **/

    public static final class Joysticks {
        public static FlightStick left = Hardware.HumanInterfaceDevices.logitechAttack3(0);
        public static FlightStick right = Hardware.HumanInterfaceDevices.logitechAttack3(1);
        public static FlightStick operator = Hardware.HumanInterfaceDevices.logitechExtreme3DPro(2);
    }

    /** CAN IDs **/

    public static final class Motors {
        public static TalonSRX leftFront = Hardware.Motors.talonSRX(1);
        public static TalonSRX leftRear = Hardware.Motors.talonSRX(2);
        public static TalonSRX leftThird = Hardware.Motors.talonSRX(3);
        public static TalonSRX rightFront = Hardware.Motors.talonSRX(4);
        public static TalonSRX rightRear = Hardware.Motors.talonSRX(5);
        public static TalonSRX rightThird = Hardware.Motors.talonSRX(6);
        public static TalonSRX climber = Hardware.Motors.talonSRX(7);
        public static TalonSRX shooter = Hardware.Motors.talonSRX(8);
        public static TalonSRX hood = Hardware.Motors.talonSRX(9);

        public static Motor feeder = Hardware.Motors.victor(0);
    }

    /** Solenoids **/

    public static final class Solenoids {
        public static Solenoid kicker = Hardware.Solenoids.doubleSolenoid(0, 1, Direction.RETRACTING);
        public static Solenoid shifter = Hardware.Solenoids.singleSolenoid(2, Direction.RETRACTING);
        public static Solenoid leftClimber = Hardware.Solenoids.singleSolenoid(3, Direction.RETRACTING);
        public static Solenoid rightClimber = Hardware.Solenoids.singleSolenoid(4, Direction.RETRACTING);

    }

    /** Constants **/

    public static final class Constants {
        static double WHEEL_DIAMETER = 4.0;
        static double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    }
}
