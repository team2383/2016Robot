package com.team2383.robot.auto;

import com.team2383.robot.ActuateHoodStop;
import com.team2383.robot.HAL;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.SetHeading;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.Spool;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class LowBarFarHighGoal extends CommandGroup {
	public LowBarFarHighGoal() {
		addParallel(new ActuateHoodStop(HAL.hoodTopLimit));
		addSequential(new DriveDistance(0.74, 122, Gear.HIGH, true));
		addParallel(new MoveHood(() -> 0.9, 0.3));
		addSequential(new GyroTurn(30));
		//addSequential(new DriveDistance(0.74, 90, Gear.HIGH, true));
		// Raise hood at 0.4 for 0.2 seconds
		addParallel(new SpoolToRPM(4));
		addSequential(new WaitCommand(3));
		addSequential(new Shoot(2));
		addSequential(new PrintCommand("Shooting!"));
		addSequential(new WaitForChildren()); // wait for spool down
	}
}
