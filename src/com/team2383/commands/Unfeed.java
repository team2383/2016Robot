package com.team2383.commands;

import com.team2383.robot.*;
import org.strongback.Strongback;
import org.strongback.command.Command;

public class Unfeed extends Command {    
    
    public Unfeed() {
    		Robot.feeder.unfeed();
    }
       

     @Override
    public boolean execute() {
    	 Robot.feeder.unfeed();
	return true;
    }
}
