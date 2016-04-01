package com.team2383.robot.auto;

import com.team2383.robot.Constants;
import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.VisionShoot;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarHighGoalFarVision extends CommandGroup {
	public LowBarHighGoalFarVision() {
		addSequential(new DriveDistance(1.0, 209, Gear.LOW, true));
		addSequential(new ActuateHoodStop(false));
		addSequential(new GyroTurn(Constants.driveHeadingMoveToVelocity, 56.5, 3));
		addSequential(new VisionShoot());
	}
}
