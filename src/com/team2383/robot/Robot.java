/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;
import org.strongback.drive.TankDrive;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.Gamepad;
import org.strongback.components.ui.FlightStick;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

    private TankDrive drive;
    private ContinuousRange leftSpeed;
    private ContinuousRange rightSpeed;

    @Override
    public void robotInit() {
        // Set up Strongback using its configurator. This is entirely optional, but we're not using
        // events or data so it's better if we turn them off. All other defaults are fine.
        Strongback.configure().recordNoEvents().recordNoData().initialize();

        Motor left = Motor.compose(Hardware.Motors.talonSRX(Config.LEFT_FRONT_MOTOR_PORT),
                                   Hardware.Motors.talonSRX(Config.LEFT_REAR_MOTOR_PORT));

        Motor right = Motor.compose(Hardware.Motors.talonSRX(Config.RIGHT_FRONT_MOTOR_PORT),
                                   Hardware.Motors.talonSRX(Config.RIGHT_REAR_MOTOR_PORT));

        drive = new TankDrive(left, right);
        

        FlightStick leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.LEFT_JOYSTICK_PORT);
	FlightStick rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.RIGHT_JOYSTICK_PORT);
        leftSpeed = leftJoystick.getLeftY();
        rightSpeed = rightJoystick.getRightY();
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.start();

        drive.tank(leftSpeed.read(), rightSpeed.read());
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

}
