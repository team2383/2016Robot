/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Solenoid.Direction;
import org.strongback.components.TalonSRX;
import org.strongback.hardware.Hardware;

public class Config {
	/** Joysticks **/

	public enum Joysticks {
		LEFT, RIGHT, OPERATOR
	}

	static int LEFT_JOYSTICK_PORT = 0;
	static int RIGHT_JOYSTICK_PORT = 1;
	static int OPERATOR_JOYSTICK_PORT = 2;

	/** CAN IDs **/

	public enum TalonSRXs {
		LEFT_FRONT, LEFT_REAR, LEFT_THIRD, RIGHT_FRONT, RIGHT_REAR, RIGHT_THIRD, CLIMBER, SHOOTER, FEEDER, HOOD;

		public TalonSRX get() {
			return Hardware.Motors.talonSRX(this.ordinal());
		}
	}

	public enum PWMMotors {
		FEEDER, HOOD;

		public Motor get() {
			return Hardware.Motors.victor(this.ordinal());
		}
	}

	/** Solenoids **/

	public enum SingleSolenoids {

		LEFT_CLIMBER(1), RIGHT_CLIMBER(2), KICKER(3);

		private final int port;
		private final Direction initialDirection;

		// extend is A, retract is B on vex manifold
		SingleSolenoids(int port) {
			this.port = port;
			this.initialDirection = Direction.RETRACTING;
		}

		SingleSolenoids(int port, Direction initialDirection) {
			this.port = port;
			this.initialDirection = initialDirection;
		}

		public Solenoid get() {
			return Hardware.Solenoids.singleSolenoid(this.port, this.initialDirection);
		}
	}

	public enum DoubleSolenoids {
		SHIFTER(0, 7);

		private final int extend;
		private final int retract;
		private final Direction initialDirection;

		// extend is A, retract is B on vex manifold
		DoubleSolenoids(int extend, int retract) {
			this.extend = extend;
			this.retract = retract;
			this.initialDirection = Direction.RETRACTING;
		}

		DoubleSolenoids(int extend, int retract, Direction initialDirection) {
			this.extend = extend;
			this.retract = retract;
			this.initialDirection = initialDirection;
		}

		public Solenoid get() {
			return Hardware.Solenoids.doubleSolenoid(this.extend, this.retract, this.initialDirection);
		}
	}

	/** Constants **/

	static double WHEEL_DIAMETER = 4.0;
	static double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
}
