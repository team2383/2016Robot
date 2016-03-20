package com.team2383.robot.auto;

import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.MoveHood;
import com.team2383.robot.commands.Shoot;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.commands.WaitForRPM;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class LowBarHighGoalConstant extends CommandGroup {
	public LowBarHighGoalConstant() {
		addParallel(new ActuateHoodStop(true));
		addSequential(new DriveDistance(0.74, 182, Gear.HIGH, true));
		addParallel(new MoveHood(() -> 0.9, 0.3));
		addSequential(new GyroTurn(49));
		addSequential(new DriveDistance(0.74, 126, Gear.HIGH, true));
		addSequential(new DriveDistance(0.74, 126, Gear.LOW, true));
		// Raise hood at 0.4 for 0.2 seconds
		addParallel(new SpoolToRPM(2800, 4));
		addSequential(new WaitForRPM(1));
		addParallel(new PrintCommand("Shooting!"));
		addSequential(new Shoot(2));
		addSequential(new WaitForChildren()); // wait for spool down
	}
}
