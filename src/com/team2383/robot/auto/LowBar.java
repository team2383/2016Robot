package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBar extends CommandGroup {
	public LowBar() {
		addSequential(new DriveDistance(0.5, 200));
		addSequential(new DriveDistance(0.5, -200));
	}
}
