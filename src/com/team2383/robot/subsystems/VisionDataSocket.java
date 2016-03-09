package com.team2383.robot.subsystems;

import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj.command.Command;

public class VisionDataSocket extends Command {

	private final BiConsumer<Double, Double> callback;

	public VisionDataSocket(BiConsumer<Double, Double> callback) {
		this.callback = callback;
	}

	@Override
	protected void initialize() {
		// TODO open vision socket
	}

	@Override
	protected void execute() {
		// TODO fetch numbers from vision target
		callback.accept(0.0, 0.0);
	}

	@Override
	protected boolean isFinished() {
		return false; // run always
	}

	@Override
	protected void end() {
		// TODO Close vision socket
	}

	@Override
	protected void interrupted() {
		this.end();
	}

}
