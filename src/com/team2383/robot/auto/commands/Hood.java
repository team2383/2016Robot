/* Created Sat Feb 13 18:15:26 EST 2016 */
package com.team2383.robot.auto.commands;

import org.strongback.command.Command;
import org.strongback.components.TalonSRX;


import com.team2383.robot.Config;

import edu.wpi.first.wpilibj.Timer;

//still need to make sure of speed inputs here
//also need encoder so for now just move it based on time
public class Hood extends Command {
	double hoodSpeed;
	TalonSRX hood;
	double time;
	
	public Hood(double speed,double time){
		this.hood = Config.Motors.hood;
		this.time = time;
		this.hoodSpeed = speed;
	}
	
	@Override
	public boolean execute(){
		hood.setSpeed(this.hoodSpeed);
		Timer.delay(time);
		return true;
	}
}
