package com.team2383.subsystems;

import com.team2383.commands.ClimberUp;
import com.team2383.commands.Feed;
import com.team2383.robot.*;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.team2383.robot.Config;

import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.components.Solenoid;
import org.strongback.components.Switch;
import org.strongback.hardware.Hardware.Motors;
import org.strongback.hardware.Hardware.Solenoids;



public class Feeder extends Subsystem {

	//feeder goes same speed whole match -- Massimo 1/25/16 @ 3:43:30 p.m
	
	public static Motor feederMotor = Motors.talon(Config.FEEDER_MOTOR_PORT);
	
	public Feeder() {
	}
	
	@Override
	protected void initDefaultCommand() {
		new Feed();
		
	}
	
	public void feed(){
		feederMotor.setSpeed(0.5);
	}
}
