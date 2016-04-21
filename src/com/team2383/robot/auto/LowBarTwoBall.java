package com.team2383.robot.auto;

import com.team2383.robot.Constants.Preset;
import com.team2383.robot.commands.ActuateHoodStop;
import com.team2383.robot.commands.AutoShoot;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.GyroTurn;
import com.team2383.robot.commands.MoveArms;
import com.team2383.robot.commands.SpoolToRPM;
import com.team2383.robot.commands.UsePreset;
import com.team2383.robot.commands.WaitForHood;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarTwoBall extends CommandGroup {
	public LowBarTwoBall() {
		addSequential(new DriveDistance(0.85, 140, Gear.HIGH, true));
		addSequential(new ActuateHoodStop(false));
		addParallel(new MoveArms(0.5, 1.0));
		addParallel(new UsePreset(Preset.courtyardTwoBall));
		addParallel(new SpoolToRPM());
		addSequential(new GyroTurn(38.5));
		addSequential(new WaitForHood());
		addSequential(new AutoShoot());
		addSequential(new UsePreset(Preset.closed));
		addSequential(new MoveArms(-0.5, 1.0));
		addSequential(new GyroTurn(115));
		addSequential(new DriveDistance(0.85, 140, Gear.HIGH, true));
	}
}
