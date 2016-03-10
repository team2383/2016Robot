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
	public HashMap<String, Double> options = getDefaultOptions();

	public abstract HashMap<String, Double> getDefaultOptions();

	public String getPrefix() {
		return this.getClass().getSimpleName();
	};

	public Double getOption(String name) {
		return options.get(name);
	}

	/**
	 * Push/pull values to SmartDashboard
	 */
	public AutoCommand() {
		options.forEach((name, value) -> {
			SmartDashboard.putNumber(getPrefix() + "-" + name, value);
		});
	}

	/**
	 * update config options
	 */
	public void update() {
		options.forEach((name, value) -> {
			options.put(name, SmartDashboard.getNumber(getPrefix() + "-" + name));
		});
	}
}
