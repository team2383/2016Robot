package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Ramparts extends CommandGroup{
	public Ramparts(){
		addSequential(new DriveDistance(0.75, 100));
		addSequential(new DriveDistance(0.75, -100));	
	}
}
