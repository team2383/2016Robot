package com.team2383.robot.auto.defenses;

import java.util.HashMap;

import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class RoughTerrain extends DriveToDefense {
	@Override
	public HashMap<String, Double> getOptionDefaults() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("Velocity", 0.6);
		options.put("Distance", -132.0);
		return options;
	};

	/**
	 * Default Low Gear
	 */
	@Override
	public SendableChooser getGearChooser() {
		SendableChooser chooser = new SendableChooser();
		chooser.addDefault(this.getName() + " Low Gear", Gear.LOW);
		chooser.addObject(this.getName() + " High Gear", Gear.HIGH);
		return chooser;
	}

	/**
	 * Default BrakeMode == false
	 */
	@Override
	public SendableChooser getBrakeChooser() {
		SendableChooser chooser = new SendableChooser();
		chooser.addDefault(this.getName() + " Coast", false);
		chooser.addObject(this.getName() + " Brake", true);
		return chooser;
	}
}
