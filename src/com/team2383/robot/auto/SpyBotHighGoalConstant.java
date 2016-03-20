package com.team2383.robot.auto;

import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class SpyBotHighGoalConstant extends CommandGroup {
	public SpyBotHighGoalConstant() {
		addSequential(new DriveDistance(0.71, 30, Gear.LOW, false));
		addParallel(new MoveHood(() -> 0.9, 0.3));
		addSequential(new SpoolToRPM(3));
		addSequential(new Shoot(2));
		addSequential(new PrintCommand("Shooting!"));
		addSequential(new DriveDistance(0.71, -20, Gear.LOW,false));
		addSequential(new GyroTurn(90));
		addSequential(new DriveDistance(0.2, -10, Gear.LOW,false));
		addSequential(new DriveDistance(0.9, 261, Gear.HIGH, true));
		addParallel(new MoveHood(() -> -0.9,0.3));
		
		addSequential(new WaitCommand(2));
	}
}
