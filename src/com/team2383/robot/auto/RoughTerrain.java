package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;



public class RoughTerrain extends CommandGroup {
	
	public RoughTerrain(){
		addSequential(new DriveDistance(0.5, 100));
		addSequential(new DriveDistance(0.5, -100));	
	}
}
