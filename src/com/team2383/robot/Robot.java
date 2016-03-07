/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;

import com.team2383.robot.subsystems.Drivetrain;
import com.team2383.robot.subsystems.Drivetrain.Gear;
import com.team2383.robot.subsystems.ShooterFeeder;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Robot extends IterativeRobot {

    private final String defaultAuto = "Default Auto";
    private final String secondAuto = "Second Auto";

    private String autoSelected;

    private static SendableChooser chooser;

    private SwitchReactor reactor;

    private Drivetrain drivetrain;
    private ShooterFeeder shooterFeeder;
    private TalonSRX arms;
    private USBCamera camera;

    @Override
    public void robotInit() {
        // Set up Strongback using its configurator. This is entirely optional,
        // but we're not using
        // events or data so it's better if we turn them off. All other defaults
        // are fine.
        Strongback.configure().recordNoEvents().recordNoData().recordNoCommands().initialize();

        reactor = Strongback.switchReactor();

        camera = new USBCamera();
        CameraServer.getInstance().setQuality(50);
        CameraServer.getInstance().startAutomaticCapture(camera);

        drivetrain = HAL.drivetrain;
        shooterFeeder = HAL.shooterFeeder;
        arms = HAL.arms;
        arms.enableBrakeMode(true);
    }

    @Override
    public void autonomousInit() {
        Strongback.start();
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
        /**
         * OPERATOR
         */

        reactor.whileTriggered(Switch.alwaysTriggered(), () -> {
            drivetrain.drive.tank(OI.tankLeftSpeed.read(), OI.tankRightSpeed.read());
        });

        reactor.onTriggered(OI.shiftDown, () -> drivetrain.shiftTo(Gear.LOW));
        reactor.onTriggered(OI.shiftUp, () -> drivetrain.shiftTo(Gear.HIGH));

        reactor.onTriggered(() -> {
            return shooterFeeder.hood.getWPILibCANTalon().isRevLimitSwitchClosed();
        } , () -> {
            Strongback.logger().debug("setting zero...");
            shooterFeeder.setHoodZero();
        });

        // feeder
        reactor.whileTriggered(OI.feedIn,
                () -> shooterFeeder.setFeederPower(1.0));
        reactor.whileTriggered(OI.feedOut,
                () -> shooterFeeder.setFeederPower(-1.0));
        reactor.onUntriggered(OI.feeding, () -> shooterFeeder.setFeederPower(0));

        // hood/shooter
        reactor.whileTriggered(Switch.alwaysTriggered(), () -> {
            shooterFeeder.hood.setSpeed(OI.hood.read());
        });

        reactor.whileTriggered(OI.spool, () -> shooterFeeder.shooter.setSpeed(1.0));
        reactor.onUntriggered(OI.spool, () -> shooterFeeder.shooter.setSpeed(0.0));

        reactor.whileTriggered(OI.shoot, () -> shooterFeeder.setFeederPower(1));
        reactor.onUntriggered(OI.shoot, () -> shooterFeeder.setFeederPower(0));

        // arms
        reactor.onTriggered(OI.extendArms, () -> arms.setSpeed(1.0));
        reactor.onTriggered(OI.retractArms, () -> arms.setSpeed(-1.0));
        reactor.onUntriggered(Switch.or(OI.extendArms, OI.retractArms), () -> arms.stop());
    }

    @Override
    public void teleopPeriodic() {
        drivetrain.tank(OI.shiftUp, OI.shiftDown, OI.tankLeftSpeed.read(), OI.tankRightSpeed.read());
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill
        // commands.
        Strongback.disable();
    }
}
