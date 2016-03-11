package com.team2383.robot.auto.defenses;

import java.util.HashMap;

import com.team2383.robot.auto.AutoCommand;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class DriveToDefense extends AutoCommand {
	public DriveToDefense() {
		super();

		addSequential(new DriveDistance(getOption("Velocity"),
				getGlobalOption("Auto Start to Ramp Distance") + getOption("Distance"), getGear(), getBrake()));
	}

	@Override
	public final HashMap<String, Double> getGlobalOptionDefaults() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("Auto Start to Ramp Distance", 122.0);
		return options;
	};

	@Override
	public HashMap<String, Double> getOptionDefaults() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("Velocity", 0.0);
		options.put("Distance", 0.0);
		return options;
	};

	/**
	 * Default High Gear
	 */
	public SendableChooser getGearChooser() {
		SendableChooser chooser = new SendableChooser();
		chooser.addDefault(this.getName() + " High Gear", Gear.HIGH);
		chooser.addObject(this.getName() + " Low Gear", Gear.LOW);
		return chooser;
	}

	/**
	 * Default BrakeMode == true
	 */
	public SendableChooser getBrakeChooser() {
		SendableChooser chooser = new SendableChooser();
		chooser.addDefault(this.getName() + " Brake", true);
		chooser.addObject(this.getName() + " Coast", false);
		return chooser;
	}

	private final Gear getGear() {
		return (Gear) getGearChooser().getSelected();
	}

	private final boolean getBrake() {
		return (boolean) getBrakeChooser().getSelected();
	}
}
