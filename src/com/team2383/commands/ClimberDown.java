package com.team2383.commands;

import com.team2383.robot.*;
import org.strongback.Strongback;
import org.strongback.command.Command;

public class ClimberDown extends Command {    
    
    public ClimberDown() {
    		System.out.println("Climber --> Down");
    		Robot.climber.down();
    }
       

     @Override
    public boolean execute() {
    	 System.out.println("Climber --> Down");
    	 Robot.climber.down();
	return true;
    }
}
