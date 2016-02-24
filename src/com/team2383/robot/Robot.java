/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import java.util.concurrent.TimeUnit;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.control.SoftwarePIDController;
import org.strongback.control.SoftwarePIDController.SourceType;
import org.strongback.drive.TankDrive;
import org.strongback.function.DoubleToDoubleFunction;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot implements PIDOutput {
	

    private final String defaultAuto = "Default Auto";
    private final String secondAuto = "Second Auto";

    private String autoSelected;

    private static SendableChooser chooser;
    
    public AHRS ahrs;
    public SoftwarePIDController turnController;
    double rotateToAngleRate;
    public static double ksR = 1;
    public static double ksL = 1;
    
    public static TankDrive drive;

    boolean rotateToAngle = false;
    double kP;
    double kI;
    double kD;
    double kF;
    double kS;
    
    public static final double kToleranceDegrees = 2.0f;
    
    Encoder wheelEncoder;
    

    @Override
    public void robotInit() {
        // Set up Strongback using its configurator. This is entirely optional,
        // but we're not using
        // events or data so it's better if we turn them off. All other defaults
        // are fine.
        Strongback.configure().recordCommands().useExecutionPeriod(25, TimeUnit.MILLISECONDS).initialize();

        TalonSRX leftFront = Config.Motors.leftFront;
        TalonSRX leftRear = Config.Motors.leftRear;
        TalonSRX rightFront = Config.Motors.rightFront;
        TalonSRX rightRear = Config.Motors.rightRear;
        
        wheelEncoder = Config.Encoders.wheelEncoder;

        Motor left = Motor.compose(leftFront, leftRear);
        Motor right = Motor.compose(rightFront, rightRear).invert();

        Motor climberPivot = Config.Motors.climber;
        Motor shooter = Config.Motors.shooter;

        Motor hood = Config.Motors.hood;
        Motor feeder = Config.Motors.feeder;

        FlightStick leftJoystick = Config.Joysticks.left;
        FlightStick rightJoystick = Config.Joysticks.right;
        FlightStick operatorJoystick = Config.Joysticks.operator;

        Solenoid shifter = Config.Solenoids.shifter;
        Solenoid leftClimber = Config.Solenoids.leftClimber;
        Solenoid rightClimber = Config.Solenoids.rightClimber;
        Solenoid kicker = Config.Solenoids.kicker;
        
        
        
        drive = new TankDrive(left, right);

		turnController = new SoftwarePIDController(SourceType.DISTANCE,(DoubleSupplier)ahrs, (DoubleConsumer)this);
        turnController.withGains(Config.Constants.kP, Config.Constants.kI,Config.Constants.kD, Config.Constants.kF);
        turnController.withInputRange(-180.0f,  180.0f);
        turnController.withOutputRange(-1.0, 1.0);
        turnController.continuousInputs(true);

        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("Second Auto", secondAuto);
        SmartDashboard.putData("Auto Choices", chooser);
        
       

        /**
         * DRIVER
         */

        Switch low = leftJoystick.getButton(4);
        Switch high = leftJoystick.getButton(5);
        Switch invertDrive = () -> !leftJoystick.getTrigger().isTriggered();
        Switch holdUpKicker = rightJoystick.getTrigger();
        Switch straightDrive = rightJoystick.getButton(4);
        Switch resetGyro = rightJoystick.getButton(11);
        

        // map -1 <-> 1 to 0-1;
        ContinuousRange expo = leftJoystick.getAxis(2).invert().mapToRange(0, 1);
        DoubleToDoubleFunction expoFunc = (x) -> {
            SmartDashboard.putNumber("expo", expo.read());
            return expo.read() * Math.pow(x, 3) + (1 - expo.read()) * x;
        };
        DoubleToDoubleFunction deadband = (x) -> {
            return Math.abs(x) <= 0.1 ? 0 : x;
        };

        ContinuousRange leftSpeed = leftJoystick.getPitch().map(deadband).map(expoFunc);
        ContinuousRange rightSpeed = rightJoystick.getPitch().map(deadband).map(expoFunc);

        ContinuousRange hoodAim = operatorJoystick.getPitch().map(deadband).map(expoFunc).mapToRange(-0.8, 0.8);

        /**
         * OPERATOR
         */

        Switch shoot = operatorJoystick.getTrigger();
        Switch spool = operatorJoystick.getThumb();
        Switch feedIn = () -> {
            return operatorJoystick.getButton(7).isTriggered() ||
                    operatorJoystick.getButton(8).isTriggered() ||
                    operatorJoystick.getButton(9).isTriggered() ||
                    operatorJoystick.getButton(10).isTriggered();
        };
        Switch feedOut = () -> {
            return operatorJoystick.getButton(11).isTriggered() ||
                    operatorJoystick.getButton(12).isTriggered();
        };

        Switch climberDown = operatorJoystick.getDPad(0).getDown();
        Switch climberUp = operatorJoystick.getDPad(0).getUp();
        Switch climberRetract = operatorJoystick.getButton(5);
        Switch climberExtend = operatorJoystick.getButton(6);

        SwitchReactor reactor = Strongback.switchReactor();

        /**
         * DRIVER
         */

        // tank drive
        reactor.whileUntriggered(invertDrive, () -> drive.tank(leftSpeed.read() * ksL, rightSpeed.read() * ksR));
        reactor.whileTriggered(invertDrive, () -> drive.tank(-rightSpeed.read() * ksR, -leftSpeed.read() * ksL));

        // shifter
        reactor.onTriggered(low, () -> {
            shifter.retract();
        });
        reactor.onUntriggered(high, () -> {
            shifter.extend();
        });

        // hold kicker up
        reactor.onTriggered(holdUpKicker, () -> {
            kicker.extend();
        });
        reactor.onUntriggered(holdUpKicker, () -> {
            kicker.retract();
        });
        
        // gyro assist
        reactor.onTriggered(resetGyro, () -> {
        	ahrs.reset();
        });
        reactor.whileTriggered(straightDrive, () -> {
        	ksL = (1+ ahrs.getAngle() / 90 ) * Config.Constants.kS;
            ksR = (1- ahrs.getAngle() / 90)  * Config.Constants.kS;
        });   
        reactor.onUntriggered(straightDrive, () -> {
        	ksR = 1; 
        	ksL = 1;
        });


        	
        /**
         * OPERATOR
         */

        // shooter
        reactor.onTriggered(spool, () -> {
            shooter.setSpeed(1.0);
        });
        reactor.onUntriggered(spool, shooter::stop);
        reactor.onTriggered(shoot, () -> {
            kicker.extend();
            feeder.setSpeed(1);
        });
        reactor.onUntriggered(shoot, () -> {
            kicker.retract();
        });

        // hood
        reactor.whileTriggered(Switch.alwaysTriggered(), () -> {
            hood.setSpeed(hoodAim.read());
        });

        // feeder
        reactor.onTriggered(feedIn, () -> {
            feeder.setSpeed(1);
        });
        reactor.onTriggered(feedOut, () -> {
            feeder.setSpeed(-1);
        });
        reactor.onUntriggered(feedIn, feeder::stop);
        reactor.onUntriggered(feedOut, feeder::stop);

        // climber pivot
        reactor.onTriggered(climberUp, () -> {
            climberPivot.setSpeed(1);
        });
        reactor.onTriggered(climberDown, () -> {
            climberPivot.setSpeed(-1);
        });
        reactor.onUntriggered(climberUp, climberPivot::stop);
        reactor.onUntriggered(climberDown, climberPivot::stop);

        // climber
        reactor.onTriggered(climberRetract, () -> {
            leftClimber.retract();
            rightClimber.retract();
        });
        reactor.onTriggered(climberExtend, () -> {
            leftClimber.extend();
            rightClimber.extend();
        });
        
    }

    @Override
    public void autonomousInit() {
        autoSelected = (String) chooser.getSelected();
        System.out.println("Auto Selected: " + autoSelected);
    }

    @Override
    public void autonomousPeriodic() {
        switch (autoSelected) {
            case secondAuto:
                // secondAuto command here
                break;
            case defaultAuto:
                // defaultAuto command here
                break;
        }
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.restart();
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill
        // commands.
        Strongback.disable();
    }
    
    @Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon navX MXP yaw angle input and PID Coefficients.    */
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }
}
