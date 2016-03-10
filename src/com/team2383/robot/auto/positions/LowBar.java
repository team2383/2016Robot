package com.team2383.robot.auto.positions;

import java.util.HashMap;

public class LowBarPosition extends DriveToBatter {
	@Override
	public final HashMap<String, Double> getOptionDefaults() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("Drive To Pivot Velocity", 0.7);
		options.put("Drive To Pivot Distance", 170.0);

		options.put("Pivot Velocity", 0.6);
		options.put("Pivot Angle", 50.0);

		options.put("Drive To Batter Velocity", 0.7);
		options.put("Drive To Batter Distance", 126.0);
		return options;
	};
}
