package com.team2383.robot.commands;

import static com.team2383.robot.HAL.vision;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.team2383.robot.subsystems.Vision;
import com.team2383.robot.subsystems.Vision.Target;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class UpdateVision extends Command {
	NetworkTable visionTable;
	private final Consumer<ArrayList<Target>> targetConsumer;

	public UpdateVision(Consumer<ArrayList<Vision.Target>> targetConsumer) {
		requires(vision);
		visionTable = NetworkTable.getTable("vision");
		this.targetConsumer = targetConsumer;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		ArrayList<Vision.Target> targets = new ArrayList<>();
		double[] distances = {};
		double[] azimuths = {};
		distances = visionTable.getNumberArray("distances", distances);
		azimuths = visionTable.getNumberArray("azimuths", azimuths);

		for (int i = 0; i < distances.length; i++) {
			targets.add(new Target(distances[i], azimuths[i]));
		}

		targets.sort((a, b) -> {
			return (int) Math.abs(a.getAzimuth()) - (int) Math.abs(b.getAzimuth());
		});

		targetConsumer.accept(targets);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
