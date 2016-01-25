package com.team2383.commands;

import com.team2383.robot.*;
import org.strongback.Strongback;
import org.strongback.command.Command;

public class Feed extends Command {    
    
    public Feed() {
    		Robot.climber.down();
    }
       

     @Override
    public boolean execute() {
    	 Robot.feeder.feed();
	return true;
    }
}
