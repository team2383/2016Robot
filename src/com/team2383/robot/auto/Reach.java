package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Reach extends CommandGroup {
	public Reach() {
		addSequential(new DriveDistance(0.5, 70));
	}
}
