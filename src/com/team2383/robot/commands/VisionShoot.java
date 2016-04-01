package com.team2383.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Automatically spool, aim, and shoot
 *
 * TODO: Possibly do the gyro turn first, than use the old direct vision turn to
 * fine tune? maybe a slower than normal gyro turn
 * 
 * @author Matthew Alonso
 */

public class VisionShoot extends CommandGroup {
	public VisionShoot() {
		addParallel(new SpoolToRPM());
		addSequential(new VisionTurn());
		addSequential(new UseVisionPreset());
		addSequential(new AutoShoot());
	}
}
