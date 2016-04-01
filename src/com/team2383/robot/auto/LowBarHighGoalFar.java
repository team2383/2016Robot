package com.team2383.robot.auto;

import com.team2383.robot.Constants.Preset;
import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.commands.UsePreset;
import com.team2383.robot.commands.WaitForHood;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarHighGoalFar extends CommandGroup {
	public LowBarHighGoalFar() {
		// addParallel(new ExtendBullBar());
		addSequential(new DriveDistance(1.0, 209, Gear.LOW, true));
		addSequential(new ActuateHoodStop(false));
		addParallel(new UsePreset(Preset.courtyardFar));
		addParallel(new SpoolToRPM());
		addSequential(new GyroTurn(56));
		addSequential(new WaitForHood());
		addSequential(new AutoShoot());
	}
}
