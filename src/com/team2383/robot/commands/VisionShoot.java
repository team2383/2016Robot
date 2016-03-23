package com.team2383.robot.commands;

import com.team2383.robot.HAL;
import com.team2383.robot.subsystems.Vision.Target;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionShoot extends CommandGroup {
	public VisionShoot() {
		Target t = HAL.vision.getNearestTarget();
	}
}
