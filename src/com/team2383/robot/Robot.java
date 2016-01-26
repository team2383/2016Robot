/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.Motor;
import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.hardware.Hardware;

import com.team2383.command.*;
import com.team2383.components.Speedometer;

import org.strongback.drive.TankDrive;
import org.strongback.components.ui.ContinuousRange;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

    private TankDrive drive;
    private TalonSRX leftFront, leftRear, rightFront, rightRear;
    private ContinuousRange angle, throttle, brake, speed;
    private SwitchReactor reactor;
    private Speedometer speedometer;
    private Switch twinJoystickTank, gamepadConsole, gamepadTank;
    
    @Override
    public void robotInit() {
        // Set up Strongback using its configurator. This is entirely optional, but we're not using
        // events or data so it's better if we turn them off. All other defaults are fine.
        Strongback.configure().recordNoEvents().recordNoData().initialize();
        
        reactor = Strongback.switchReactor();
        
        
        CANTalon leftFront_WPI = new CANTalon(Config.LEFT_FRONT_MOTOR_PORT);
        CANTalon rightRear_WPI = new CANTalon(Config.RIGHT_REAR_MOTOR_PORT);
        
        
        TalonSRX leftFront = Hardware.Motors.talonSRX(leftFront_WPI);
        TalonSRX rightRear = Hardware.Motors.talonSRX(rightRear_WPI);

        leftFront_WPI.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        rightRear_WPI.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        
        TalonSRX leftRear = Hardware.Motors.talonSRX(Config.LEFT_REAR_MOTOR_PORT);
        TalonSRX rightFront = Hardware.Motors.talonSRX(Config.RIGHT_FRONT_MOTOR_PORT);
    

        Motor left = Motor.compose(leftFront, leftRear);

        Motor right = Motor.compose(rightFront, rightRear).invert();
        
        speedometer = Speedometer.create(speed -> {
        	//revolution/sec to in/sec
        	speed *= Config.WHEEL_CIRCUMFERENCE;
        	
        	SmartDashboard.putNumber("s", speed/12.0);
        	return speed;
        	
        }, leftFront_WPI::getPulseWidthVelocity,
           rightRear_WPI::getPulseWidthVelocity);

        drive = new TankDrive(left, right);
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.restart();
    }

    @Override
    public void teleopPeriodic() {
    	//Strongback.submit(new GamepadDriveConsole(drive, speedometer, Hardware.HumanInterfaceDevices.logitechF310(0)));
    	Strongback.submit(new SingleJoystickDriveConsole(drive, speedometer, Hardware.HumanInterfaceDevices.logitechAttack3D(0)));
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();   
    }
    
    @Override
    public void disabledPeriodic() {
    }

}
