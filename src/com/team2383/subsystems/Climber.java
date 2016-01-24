package com.team2383.subsystems;

import com.team2383.commands.ClimberUp;
import com.team2383.robot.*;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.strongback.Strongback;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;
import org.strongback.hardware.Hardware.Solenoids;



public class Climber extends Subsystem {

	private static Solenoid leftClimber , rightClimber;
	private static boolean isUp = false;
	public static Switch climbButton;
	
	public Climber() {
		
		leftClimber = Solenoids.doubleSolenoid(Config.LEFT_EXTEND_CLIMBER_PORT, 
											  Config.LEFT_RETRACT_CLIMBER_PORT,
											  Solenoid.Direction.STOPPED);
		
		rightClimber = Solenoids.doubleSolenoid(Config.RIGHT_EXTEND_CLIMBER_PORT,
											   Config.RIGHT_RETRACT_CLIMBER_PORT,
											   Solenoid.Direction.STOPPED);
		
	}
	
	@Override
	protected void initDefaultCommand() {
		new ClimberUp();
		
	}
	
	
	public void up(){
		leftClimber.extend();
		rightClimber.extend();
		isUp = true;
	}

	public void down(){
		leftClimber.retract();
		rightClimber.retract();
		isUp = false;
	}
	
}
