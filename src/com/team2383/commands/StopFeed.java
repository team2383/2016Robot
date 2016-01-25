package com.team2383.commands;

import com.team2383.robot.*;
import org.strongback.Strongback;
import org.strongback.command.Command;

public class StopFeed extends Command {    
    
    public StopFeed() {
    	Robot.feeder.stop();
    }
       

     @Override
    public boolean execute() {
    	 Robot.feeder.stop();
	return true;
    }
}
