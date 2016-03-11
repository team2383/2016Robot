package com.team2383.robot.auto.defenses;

import java.util.HashMap;

public class Lowbar extends DriveToDefense {
	@Override
	public HashMap<String, Double> getOptionDefaults() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("Velocity", 0.6);
		options.put("Distance", 130.0);
		return options;
	};
}
