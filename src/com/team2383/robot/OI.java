package com.team2383.robot;

import static com.team2383.robot.HAL.arms;
import static com.team2383.robot.HAL.drivetrain;
import static com.team2383.robot.HAL.feeder;
import static com.team2383.robot.HAL.hoodTopLimit;

import java.util.function.DoubleSupplier;

import com.team2383.ninjaLib.DPadButton;
import com.team2383.ninjaLib.DPadButton.Direction;
import com.team2383.ninjaLib.WPILambdas;
import com.team2383.robot.commands.SetState;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;
import com.team2383.robot.subsystems.Arms;
import com.team2383.robot.subsystems.Drivetrain.Gear;
import com.team2383.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.

	/* Sticks */
	public static Joystick left = new Joystick(0);
	public static Joystick right = new Joystick(1);

	public static DoubleSupplier leftSpeed = left::getY;
	public static DoubleSupplier rightSpeed = right::getY;
	public static Button shiftDown = new JoystickButton(left, 6);
	public static Button shiftUp = new JoystickButton(right, 11);

	public static Joystick operator = new Joystick(2);

	public static DoubleSupplier hood = operator::getY;

	public static Button feedIn = new JoystickButton(operator, 8);
	public static Button feedOut = new JoystickButton(operator, 12);

	public static Button extendArms = new DPadButton(operator, Direction.UP);
	public static Button retractArms = new DPadButton(operator, Direction.DOWN);

	public static Button shoot = new JoystickButton(operator, 1); // trigger
	public static Button spool = new JoystickButton(operator, 2); // thumb

	public static Button closeHood = new JoystickButton(operator, 4);
	public static Button hoodNear = new JoystickButton(operator, 9);
	public static Button hoodFar = new JoystickButton(operator, 6);

	// use buttons
	public OI() {
		shiftDown.whenPressed(WPILambdas.createCommand(() -> {
			drivetrain.shiftTo(Gear.LOW);
			return true;
		}));

		shiftUp.whenPressed(WPILambdas.createCommand(() -> {
			drivetrain.shiftTo(Gear.HIGH);
			return true;
		}));

		feedIn.whenPressed(new SetState<Feeder.State>(feeder, Feeder.State.FEEDING, Feeder.State.STOPPED));
		feedOut.whenPressed(new SetState<Feeder.State>(feeder, Feeder.State.OUTFEEDING, Feeder.State.STOPPED));

		extendArms.whenPressed(new SetState<Arms.State>(arms, Arms.State.EXTENDING, Arms.State.STOPPED));
		retractArms.whenPressed(new SetState<Arms.State>(arms, Arms.State.RETRACTING, Arms.State.STOPPED));

		spool.whenPressed(new Spool());
		shoot.whenPressed(new Shoot());

		if (Constants.useMechanicalHoodPresets) {
			hoodFar.toggleWhenActive(new ActuateHoodStop(hoodTopLimit));
		} else {
			// setup hood presets
		}
	}
}
