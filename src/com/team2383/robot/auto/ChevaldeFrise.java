package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.MoveArms;
import com.team2383.robot.subsystems.Arms;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ChevaldeFrise extends CommandGroup{
	public ChevaldeFrise(){
		addSequential(new DriveDistance(0.5, 36));
		addSequential(new MoveArms(Arms.State.EXTENDING));
		addSequential(new DriveDistance(0.5,-12));
		addSequential(new DriveDistance(0.5,36));
		addParallel(new MoveArms(Arms.State.RETRACTING));
		addSequential(new WaitCommand(1));
	}
}
