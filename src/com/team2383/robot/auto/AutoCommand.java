package com.team2383.robot.auto;

import java.util.HashMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Defines an Auto CommandGroup that has numeric parameters that can be changed
 * with smartDashboard
 *
 * @author Matthew Alonso
 *
 */
public abstract class AutoCommand extends CommandGroup {
	public HashMap<String, Double> options = getOptionDefaults();
	public HashMap<String, Double> globalOptions = getGlobalOptionDefaults();

	public HashMap<String, Double> getOptionDefaults() {
		return new HashMap<>();
	};

	public HashMap<String, Double> getGlobalOptionDefaults() {
		return new HashMap<>();
	};

	public String getPrefix() {
		return this.getName();
	};

	public Double getGlobalOption(String name) {
		return SmartDashboard.getNumber("Auto Parameter -" + name);
	}

	public Double getOption(String name) {
		return SmartDashboard.getNumber(getPrefix() + "-" + name);
	}

	/**
	 * Push/pull values to SmartDashboard
	 */
	public AutoCommand() {
		options.forEach((name, value) -> {
			SmartDashboard.putNumber(getPrefix() + "-" + name, value);
		});
	}
}
