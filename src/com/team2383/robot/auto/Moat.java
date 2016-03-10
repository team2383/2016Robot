package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Moat extends CommandGroup{
	public Moat(){
		addSequential(new DriveDistance(1.0, 100));
		addSequential(new DriveDistance(1.0, -100));	
	}
}
