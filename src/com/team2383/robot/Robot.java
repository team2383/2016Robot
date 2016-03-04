/* Created Fri Jan 15 15:05:28 EST 2016 */
package com.team2383.robot;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.Switch;

import com.team2383.robot.control.FeedInBall;
import com.team2383.robot.control.ShootBall;
import com.team2383.robot.subsystems.Drivetrain;
import com.team2383.robot.subsystems.Drivetrain.Gear;
import com.team2383.robot.subsystems.ShooterFeeder;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends IterativeRobot {

    private final String defaultAuto = "Default Auto";
    private final String secondAuto = "Second Auto";

    private String autoSelected;

    private static SendableChooser chooser;

    private SwitchReactor reactor;

    private Drivetrain drivetrain;
    private ShooterFeeder shooterFeeder;
    private Motor arms;

    @Override
    public void robotInit() {
        // Set up Strongback using its configurator. This is entirely optional,
        // but we're not using
        // events or data so it's better if we turn them off. All other defaults
        // are fine.
        Strongback.configure().recordNoEvents().recordNoData().recordNoCommands().initialize();

        reactor = Strongback.switchReactor();

        drivetrain = HAL.drivetrain;
        shooterFeeder = HAL.shooterFeeder;
        arms = HAL.arms;
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

        // feeder
        reactor.onTriggeredSubmit(OI.feedIn,
                () -> new FeedInBall(shooterFeeder));
        reactor.onUntriggered(OI.feedIn,
                () -> Command.cancel(shooterFeeder));

        reactor.whileTriggered(OI.feedOut,
                () -> shooterFeeder.setFeederPower(-1.0));
        reactor.onUntriggered(OI.feedOut,
                () -> shooterFeeder.setFeederPower(0));

        // hood/shooter
        reactor.whileTriggered(OI.manualHood, () -> {
            shooterFeeder.setHoodPower(OI.hood.read());
        });
        reactor.onUntriggered(OI.manualHood, () -> {
            shooterFeeder.setHoldHoodPosition();
        });

        reactor.onTriggeredSubmit(OI.shoot, () -> new ShootBall(shooterFeeder));
        reactor.onUntriggered(OI.shoot, () -> {
            Command.cancel(shooterFeeder);
        });

        // presets
        // reactor.onTriggered(OI.presetCloseHoodAndStopShooter, shooterFeeder::closeAndStop);
        // reactor.onTriggered(OI.presetBatter, () -> shooterFeeder.usePreset(Preset.batter));
        // reactor.onTriggered(OI.presetClose, () -> shooterFeeder.usePreset(Preset.courtyardClose));
        // reactor.onTriggered(OI.presetFar, () -> shooterFeeder.usePreset(Preset.courtyardFar));

        // arms
        reactor.onTriggered(OI.extendArms, () -> arms.setSpeed(1.0));
        reactor.onTriggered(OI.retractArms, () -> arms.setSpeed(1.0));
        reactor.onUntriggered(Switch.or(OI.extendArms, OI.retractArms), () -> arms.stop());
    }

    @Override
    public void teleopPeriodic() {
        if (OI.shiftUp.isTriggered()) {
            drivetrain.shiftTo(Gear.HIGH);
        } else if (OI.shiftDown.isTriggered()) {
            drivetrain.shiftTo(Gear.LOW);
        }

        drivetrain.drive.tank(OI.tankLeftSpeed.read(), OI.tankRightSpeed.read());
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill
        // commands.
        Strongback.disable();
    }
}
