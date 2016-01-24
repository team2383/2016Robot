package com.team2383.commands;

import com.team2383.robot.*;
import org.strongback.Strongback;
import org.strongback.command.Command;

public class ClimberUp extends Command {  
    
    public ClimberUp() {
    		System.out.println("Climber --> Up");
    		Robot.climber.up();
    }
       

     @Override
    public boolean execute() {
    	 System.out.println("Climber --> Down");
    	 Robot.climber.up();
	return true;
    }
}
