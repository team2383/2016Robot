package com.team2383.robot.auto.commands;

import org.strongback.command.Command;
import org.strongback.components.TalonSRX;


import com.team2383.robot.Config;
//(0.0 -> 1.0) is feed in ; (-1.0 -> 0.0) is feed out
public class Feed extends Command {
	double feedSpeed;
	TalonSRX feeder;
	
	public Feed(double speed){
		feeder = Config.Motors.shooter;
		this.feedSpeed = speed;
	}
	
	@Override
	public boolean execute(){
		feeder.setSpeed(this.feedSpeed);
		return true;
	}
}
