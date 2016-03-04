package com.team2383.robot.control;

import org.strongback.command.Command;

import com.team2383.robot.OI;
import com.team2383.robot.subsystems.Drivetrain;
import com.team2383.robot.subsystems.Drivetrain.Gear;

/* Handles multiple features:
 *  - auto shift
 */

public class TeleopDrive extends Command {
    private final Drivetrain drivetrain;

    public TeleopDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    @Override
    public boolean execute() {
        if (OI.shiftUp.isTriggered()) {
            drivetrain.shiftTo(Gear.HIGH);
        } else if (OI.shiftDown.isTriggered()) {
            drivetrain.shiftTo(Gear.LOW);
        }

        drivetrain.drive.tank(OI.tankLeftSpeed.read(), OI.tankRightSpeed.read());
        return false;
    }
}
