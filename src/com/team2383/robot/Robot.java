/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.Motor;
import org.strongback.components.Motor.Direction;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;
import org.strongback.hardware.Hardware;
import org.strongback.hardware.Hardware.Solenoids;

import com.team2383.commands.ClimberDown;
import com.team2383.commands.ClimberUp;
import com.team2383.commands.ShifterExtend;
import com.team2383.commands.ShifterRetract;
import com.team2383.subsystems.Climber;

import org.strongback.drive.TankDrive;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

    public static TankDrive drive;
    private static FlightStick leftJoystick;
    private static FlightStick rightJoystick;
    private static FlightStick operatorJoystick;
    private static ContinuousRange leftSpeed;
    private static ContinuousRange rightSpeed;
    private static Switch shifter;
    public static Switch climberButton;
    private final String defaultAuto = "Default Auto";
    private final String secondAuto = "Second Auto";
    String autoSelected;
    SendableChooser chooser;
    public static Solenoid leftSolenoidShifter;
    public static Solenoid rightSolenoidShifter;
    public static boolean isExtended;
    public static final Climber climber = new Climber();
    public static SwitchReactor climberReactor = Strongback.switchReactor();
    public static SwitchReactor shifterReactor = Strongback.switchReactor();


    @Override
    public void robotInit() {
        // Set up Strongback using its configurator. This is entirely optional, but we're not using
        // events or data so it's better if we turn them off. All other defaults are fine.
        Strongback.configure().recordNoEvents().recordNoData().initialize();

        Motor left = Motor.compose(Hardware.Motors.talonSRX(Config.LEFT_FRONT_MOTOR_PORT),
                                   Hardware.Motors.talonSRX(Config.LEFT_REAR_MOTOR_PORT));

        Motor right = Motor.compose(Hardware.Motors.talonSRX(Config.RIGHT_FRONT_MOTOR_PORT),
                                   Hardware.Motors.talonSRX(Config.RIGHT_REAR_MOTOR_PORT));
        
        leftSolenoidShifter= Solenoids.doubleSolenoid(Config.LEFT_EXTEND_SHIFTER_PORT,
        		 									  Config.LEFT_RETRACT_SHIFTER_PORT,
        		 									  Solenoid.Direction.STOPPED);
        rightSolenoidShifter= Solenoids.doubleSolenoid(Config.RIGHT_EXTEND_SHIFTER_PORT,
        		 									  Config.RIGHT_RETRACT_SHIFTER_PORT,
        		 									  Solenoid.Direction.STOPPED);
        isExtended = false;

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
        Strongback.start();

        drive.tank(leftSpeed.read(), rightSpeed.read());
        
        //joystick buttons
        shifter = rightJoystick.getButton(3);    
        climberButton = operatorJoystick.getButton(3); // might have to update this
    }

    @Override
    public void teleopPeriodic() {
    	
    	shifterPeriodic();
    	climberPeriodic();
    	
    }
    
    private void climberPeriodic(){
    	climberReactor.onTriggered(climberButton, ()->Strongback.submit(new ClimberUp()));
    	climberReactor.onUntriggered(climberButton,()->Strongback.submit(new ClimberDown()));
    }
    
    private void shifterPeriodic(){
    	//checks shifter
    	shifterReactor.onTriggered(shifter, ()->Strongback.submit(new ShifterExtend()));
    	shifterReactor.onUntriggered(shifter, ()->Strongback.submit(new ShifterRetract()));
    }
    
    
    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
        
        //retract shifter
        Strongback.submit(new ShifterRetract());
        }
}
