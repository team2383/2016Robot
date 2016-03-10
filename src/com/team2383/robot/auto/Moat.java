package com.team2383.robot.auto;

import java.util.HashMap;

import com.team2383.robot.commands.DriveDistance;


public class Moat extends AutoCommand{
	public Moat(){
		addSequential(new DriveDistance(1.0, getOption("AutoLine to Courtyard Outer Works")));
		addSequential(new DriveDistance(1.0, -getOption("AutoLine to Courtyard Outer Works")));	
	}
	
	@Override
	public HashMap<String, Double> getDefaultOptions() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("AutoLine to Courtyard Outer Works", 125.0);
		return options;
	}
}
