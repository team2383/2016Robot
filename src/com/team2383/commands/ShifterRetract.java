package com.team2383.commands;

import com.team2383.robot.*;
import org.strongback.Strongback;
import org.strongback.command.Command;

public class ShifterRetract extends Command {  
    
    public ShifterRetract() {
    		System.out.println("Shifter --> Retract");
    }
       

     @Override
    public boolean execute() {
    	 System.out.println("Shifter --> Retract");
    	 Robot.leftSolenoidShifter.retract();
 		 Robot.rightSolenoidShifter.retract();
 		 Robot.isExtended = false;
	return true;
    }
}