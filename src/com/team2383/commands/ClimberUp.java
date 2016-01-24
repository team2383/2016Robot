package com.team2383.commands;

import com.team2383.robot.*;
import org.strongback.Strongback;
import org.strongback.command.Command;

public class ClimberUp extends Command {
	double distance;
    double power;    
    
    public ClimberUp() {
    		System.out.println("Climber --> Up");
    }
       

     @Override
    public boolean execute() {
    	 Robot.climber.up();
	return true;
    }
}
