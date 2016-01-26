/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.Motor;
import org.strongback.components.TalonSRX;
import org.strongback.hardware.Hardware;

import com.team2383.command.*;
import com.team2383.components.Speedometer;

import org.strongback.drive.TankDrive;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

    private static TankDrive drive;
    private static FlightStick leftJoystick;
    private static FlightStick rightJoystick;
    private static FlightStick operatorJoystick;
    private static ContinuousRange leftSpeed;
    private static ContinuousRange rightSpeed;
    private static Speedometer speedometer;
    
    private final String defaultAuto = "Default Auto";
    private final String secondAuto = "Second Auto";
    private String autoSelected;
    private SendableChooser chooser;
    
    
    private static Solenoid leftSolenoidShifter;
    private static Solenoid rightSolenoidShifter;
    //switch reactors
    private static SwitchReactor reactor = Strongback.switchReactor();

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
        
        leftSolenoidShifter= Hardware.Solenoids.doubleSolenoid(Config.LEFT_EXTEND_SHIFTER_PORT,
        		 									  Config.LEFT_RETRACT_SHIFTER_PORT,
        		 									  Solenoid.Direction.STOPPED);
        rightSolenoidShifter= Hardware.Solenoids.doubleSolenoid(Config.RIGHT_EXTEND_SHIFTER_PORT,
        		 									  Config.RIGHT_RETRACT_SHIFTER_PORT,
        		 									  Solenoid.Direction.STOPPED);
        
        
        speedometer = Speedometer.create(speed -> {
        	//revolution/sec to in/sec
        	speed *= Config.WHEEL_CIRCUMFERENCE;
        	
        	SmartDashboard.putNumber("s", speed/12.0);
        	return speed;
        	
        }, leftFront_WPI::getPulseWidthVelocity,
           rightRear_WPI::getPulseWidthVelocity);

        drive = new TankDrive(left, right);
        leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.LEFT_JOYSTICK_PORT);
        rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.RIGHT_JOYSTICK_PORT);
        operatorJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(Config.OPERATOR_JOYSTICK_PORT);
        leftSpeed = leftJoystick.getYaw();
        rightSpeed = rightJoystick.getYaw(); 
        
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("Second Auto", secondAuto);	
        SmartDashboard.putData("Auto Choices", chooser);
        
    }

    public void autonomousInit(){
    	autoSelected = (String) chooser.getSelected();
    	System.out.println("Auto Selected: " + autoSelected);
    }
    
    public void autonomousPeriodic(){
    	switch(autoSelected){
    	case secondAuto:
    		//secondAuto command here
    		break;
    	case defaultAuto:
    		//defaultAuto command here
    		break; 
    	}
    }
    
    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.restart();

        drive.tank(leftSpeed.read(), rightSpeed.read());
    }

    @Override
    public void teleopPeriodic() {
    	//Strongback.submit(new GamepadDriveConsole(drive, speedometer, Hardware.HumanInterfaceDevices.logitechF310(0)));
    	Strongback.submit(new TwinJoystickDriveConsole(drive, speedometer, Hardware.HumanInterfaceDevices.logitechAttack3D(0)));
    }
    
    
    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
<<<<<<< HEAD
        Strongback.disable();   
    }
    
    @Override
    public void disabledPeriodic() {
    }

=======
        Strongback.disable();
        
        //retract shifter
        Strongback.submit(new ShifterRetract());
        }
>>>>>>> cd7411027aa5b2d9f7f2287f79873e4e4f4c11db
}
