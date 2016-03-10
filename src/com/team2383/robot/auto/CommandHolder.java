package com.team2383.robot.auto;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;

public class CommandHolder {
	private final Supplier<Command> commandSupplier;

	public CommandHolder(Supplier<Command> commandSupplier) {
		this.commandSupplier = commandSupplier;
	}

	public Command get() {
		return commandSupplier.get();
	}
}
