package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/*
 * Points: 20
 * 
 * Breaches Low Bar Twice
 */

public class LowBar extends CommandGroup {
	public LowBar() {
		addSequential(new DriveDistance(0.5, 100));
		addSequential(new DriveDistance(0.5, -100));
	}
}
