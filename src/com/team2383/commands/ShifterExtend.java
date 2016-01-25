package com.team2383.commands;

import com.team2383.robot.*;
import org.strongback.Strongback;
import org.strongback.command.Command;

public class ShifterExtend extends Command {  
    
    public ShifterExtend() {
    		System.out.println("Shifter --> Extended");
    }
       

     @Override
    public boolean execute() {
    	 System.out.println("Shifter --> Extended");
    	 Robot.leftSolenoidShifter.extend();
 		 Robot.rightSolenoidShifter.extend();
 		 Robot.isExtended = true;
	return true;
    }
}