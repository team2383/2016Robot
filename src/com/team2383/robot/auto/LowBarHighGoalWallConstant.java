package com.team2383.robot.auto;

import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarHighGoalWallConstant extends CommandGroup {
	public LowBarHighGoalWallConstant() {
		addSequential(new ActuateHoodStop(true));
		addSequential(new DriveDistance(0.74, 272, Gear.HIGH, true));
		addParallel(new MoveHood(() -> 0.9, 0.3));
		addSequential(new GyroTurn(90));
		addSequential(new DriveDistance(0.74, 90, Gear.HIGH, true));
	}
}
