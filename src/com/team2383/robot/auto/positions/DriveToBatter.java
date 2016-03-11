package com.team2383.robot.auto.positions;

import java.util.HashMap;

import com.team2383.robot.auto.AutoCommand;
import com.team2383.robot.commands.DriveDistance;
import com.team2383.robot.commands.SetHeading;
import com.team2383.robot.subsystems.Drivetrain.Gear;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public abstract class DriveToBatter extends AutoCommand {
	public class DriveToBatterParams {
		double distanceToPivot;
		double pivot;
		double distanceToBatter;

		public DriveToBatterParams(double distanceToPivot, double pivot, double distanceToBatter) {
			this.distanceToPivot = distanceToPivot;
			this.pivot = pivot;
			this.distanceToBatter = distanceToBatter;
		}
	}

	public DriveToBatter() {
		double distanceToPivot = getOption("Drive To Pivot Distance");
		double pivot = getOption("Pivot Angle");
		if (distanceToPivot >= 0) {
			addSequential(
					new DriveDistance(getOption("Drive To Pivot Velocity"), distanceToPivot, getGear(), getBrake()));
		}

		if (pivot != 0) {
			addSequential(new SetHeading(getOption("Pivot Velocity"), pivot));
		}

		addSequential(new DriveDistance(getOption("Drive To Batter Velocity"), getOption("Drive To Batter Distance"),
				getGear(), getBrake()));
	}

	@Override
	public HashMap<String, Double> getOptionDefaults() {
		HashMap<String, Double> options = new HashMap<>();
		options.put("Drive To Pivot Velocity", 0.0);
		options.put("Drive To Pivot Distance", 0.0);
		options.put("Pivot Velocity", 0.0);
		options.put("Pivot Angle", 0.0);
		options.put("Drive To Batter Velocity", 0.0);
		options.put("Drive To Batter Distance", 0.0);
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
