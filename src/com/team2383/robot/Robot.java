/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import java.util.concurrent.TimeUnit;
import java.util.function.DoubleUnaryOperator;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.ui.Gamepad;
import org.strongback.control.SoftwarePIDController;
import org.strongback.control.SoftwarePIDController.SourceType;
import org.strongback.drive.TankDrive;
import org.strongback.function.DoubleToDoubleFunction;
import org.strongback.hardware.Hardware;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

    private final String defaultAuto = "Default Auto";
    private final String secondAuto = "Second Auto";

    private String autoSelected;

    private static SendableChooser chooser;

    @Override
    public void robotInit() {
        // Set up Strongback using its configurator. This is entirely optional,
        // but we're not using
        // events or data so it's better if we turn them off. All other defaults
        // are fine.
        Strongback.configure().recordCommands().useExecutionPeriod(20, TimeUnit.MILLISECONDS).initialize();

        TalonSRX leftFront = Config.Motors.leftFront;
        TalonSRX leftRear = Config.Motors.leftRear;
        TalonSRX rightFront = Config.Motors.rightFront;
        TalonSRX rightRear = Config.Motors.rightRear;

        Motor left = Motor.compose(leftFront, leftRear);
        Motor right = Motor.compose(rightFront, rightRear).invert();

        Motor climberPivot = Config.Motors.climber;
        Motor shooter = Config.Motors.shooter;

        Motor hood = Config.Motors.hood;
        Motor feeder = Config.Motors.feeder;

        Gamepad driver = Hardware.HumanInterfaceDevices.logitechF310(0);
        FlightStick operatorJoystick = Hardware.HumanInterfaceDevices.logitechAttack3(2);

        Solenoid shifter = Config.Solenoids.shifter;
        Solenoid leftClimber = Config.Solenoids.leftClimber;
        Solenoid rightClimber = Config.Solenoids.rightClimber;
        Solenoid kicker = Config.Solenoids.kicker;

        TankDrive drive = new TankDrive(left, right);

        AHRS navX = new AHRS(SPI.Port.kMXP);

        SoftwarePIDController straightDriveController = new SoftwarePIDController(SourceType.DISTANCE, navX::getYaw,
                (double x) -> x++)
                        .withGains(0.03, 0.0, 0.0, 0.0)
                        .withTolerance(2.0)
                        .withInputRange(-180.0, 180)
                        .withOutputRange(-1.0, 1.0)
                        .withTarget(0.0);

        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("Second Auto", secondAuto);
        SmartDashboard.putData("Auto Choices", chooser);

        /**
         * DRIVER
         */

        Switch low = driver.getLeftTriggerAsSwitch();
        Switch high = driver.getRightTriggerAsSwitch();
        Switch invertDrive = () -> !driver.getLeftStick().isTriggered();
        Switch holdUpKicker = driver.getRightBumper();

        // map -1 <-> 1 to 0-1;
        ContinuousRange expo = operatorJoystick.getThrottle().mapToRange(0, 1);

        DoubleToDoubleFunction expoFunc = (x) -> {
            SmartDashboard.putNumber("expo", expo.read());
            return expo.read() * Math.pow(x, 3) + (1 - expo.read()) * x;
        };
        DoubleToDoubleFunction deadband = (x) -> {
            return Math.abs(x) <= 0.1 ? 0 : x;
        };

        ContinuousRange speed = driver.getRightY().map(deadband).map(expoFunc);
        ContinuousRange turn = driver.getLeftX().map(deadband).map(expoFunc);

        DoubleToDoubleFunction speedApply = (value) -> {
            DoubleUnaryOperator result = (x) -> x;
            if (invertDrive.isTriggered()) {
                result = result.andThen((x) -> -speed.read());
            }
            return result.applyAsDouble(value);
        };

        DoubleToDoubleFunction turnApply = (value) -> {
            DoubleUnaryOperator result = (x) -> x;
            if (invertDrive.isTriggered()) {
                result = result.andThen((x) -> -turn.read());
            }
            return result.applyAsDouble(value);
        };

        speed.map(speedApply);
        turn.map(turnApply);

        ContinuousRange hoodAim = operatorJoystick.getPitch().map(deadband).map(expoFunc).mapToRange(-1, 1);

        /**
         * OPERATOR
         */

        Switch shoot = operatorJoystick.getTrigger();
        Switch spool = operatorJoystick.getButton(4);
        Switch feedIn = operatorJoystick.getButton(3);
        Switch feedOut = operatorJoystick.getButton(2);
        Switch climberDown = operatorJoystick.getButton(8);
        Switch climberUp = operatorJoystick.getButton(7);
        Switch climberRetract = operatorJoystick.getButton(5);
        Switch climberExtend = operatorJoystick.getButton(6);

        SwitchReactor reactor = Strongback.switchReactor();

        /**
         * DRIVER
         */

        // tank drive
        reactor.whileTriggered(Switch.alwaysTriggered(), () -> drive.arcade(speed.read(), turn.read()));

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

        /**
         * OPERATOR
         */

        // shooter
        reactor.onTriggered(spool, () -> {
            shooter.setSpeed(1.0);
        });
        reactor.onUntriggered(spool, shooter::stop);
        reactor.onUntriggered(shoot, feeder::stop);
        reactor.onTriggered(shoot, () -> {
            kicker.extend();
            feeder.setSpeed(1);
        });
        reactor.onUntriggered(shoot, () -> {
            kicker.retract();
            feeder.setSpeed(-1);
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
}
