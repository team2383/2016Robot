package com.team2383.robot.auto.positions;

import java.util.HashMap;

public class Center extends DriveToBatter {
	@Override
	public final HashMap<String, Double> getOptionDefaults() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("Drive To Pivot Velocity", 0.0);
		options.put("Drive To Pivot Distance", 0.0);

		options.put("Pivot Velocity", 0.0);
		options.put("Pivot Angle", 0.0);

		options.put("Drive To Batter Velocity", 0.7);
		options.put("Drive To Batter Distance", 50.0);
		return options;
	};
}
