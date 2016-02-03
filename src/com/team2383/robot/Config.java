/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

public class Config {
	/** Joysticks **/

	static int LEFT_JOYSTICK_PORT = 0;
	static int RIGHT_JOYSTICK_PORT = 1;
	static int OPERATOR_JOYSTICK_PORT = 2;

	/** CAN IDs **/

	static int LEFT_FRONT_MOTOR_PORT = 1;
	static int LEFT_REAR_MOTOR_PORT = 2;
	// static int LEFT_MOTOR_THIRD_PORT = 3;
	static int RIGHT_FRONT_MOTOR_PORT = 4;
	static int RIGHT_REAR_MOTOR_PORT = 5;
	// static int RIGHT_MOTOR_THIRD_PORT = 6;

	public static int CLIMBER_MOTOR_PORT = 7;
	public static int SHOOTER_MOTOR_PORT = 8;

	public static int FEEDER_MOTOR_PORT = 0;
	public static int HOOD_MOTOR_PORT = 7;

	/** Solenoids **/

	// for shifter
	static int RIGHT_EXTEND_SHIFTER_PORT = 0;
	static int RIGHT_RETRACT_SHIFTER_PORT = 1;

	static int LEFT_EXTEND_SHIFTER_PORT = 2;
	static int LEFT_RETRACT_SHIFTER_PORT = 3;

	// for climber
	static int RIGHT_EXTEND_CLIMBER_PORT = 4;
	static int RIGHT_RETRACT_CLIMBER_PORT = 5;
	static int LEFT_EXTEND_CLIMBER_PORT = 6;
	static int LEFT_RETRACT_CLIMBER_PORT = 7;

	/** Constants **/

	static double WHEEL_DIAMETER = 4.0;
	static double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
}
