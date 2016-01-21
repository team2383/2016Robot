package com.team2383.auto;

import org.strongback.command.Command;

import com.team2383.robot.Robot;


/**
 *												
 */
public class Move extends Command {
    double distance;
    double power;    
    
    public Move(double distance) {
    	this.distance = distance;
     	this.power = 0.5;	
    }
    
    public Move(double distance,double power){
	    this.distance = distance;
	    this.power = power;
    }    

     @Override
    public boolean execute() {
	double driveSpeed = distance > 0 ? power : -power;
    	Robot.drive.tank(driveSpeed,driveSpeed);
	return true;
    }
}
