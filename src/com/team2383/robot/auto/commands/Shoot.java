/* Created Sat Feb 13 18:09:44 EST 2016 */
package com.team2383.robot.auto.commands;

import com.team2383.robot.Config;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.TalonSRX;

import edu.wpi.first.wpilibj.Timer;

// 0 -> 1.0 is shoot, so limit our inputs accordingly
public class Shoot extends Command {
	double wheelSpeed;
	TalonSRX shooter;
	Motor feeder;
	Solenoid kicker;
	
	public Shoot(double speed){
		shooter = Config.Motors.shooter;
		feeder = Config.Motors.feeder;
		kicker = Config.Solenoids.kicker;
		this.wheelSpeed = speed;
	}
	
	@Override
	public boolean execute(){
		shooter.setSpeed(this.wheelSpeed);
		Timer.delay(2);
		kicker.extend();
		feeder.setSpeed(1.0);
		return true;
	}
}
