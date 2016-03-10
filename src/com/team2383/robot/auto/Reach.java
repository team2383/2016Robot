package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.Shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/*
 * Points: 2
 * 
 * Simply reaches a defense
 */

public class Reach extends CommandGroup {
	public Reach() {
		addSequential(new DriveDistance(0.5,24));
		// addSequential(new Shoot(5));
		// addSequential(new DriveDistance(0.66, 70));
		// addSequential(new PrintCommand("DRIVE DONE!"));
		// addSequential(new SetHeading(90));
	}
}
