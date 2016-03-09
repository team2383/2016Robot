package com.team2383.ninjaLib;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class WPILambdas {
	public static Button createButton(BooleanSupplier lambda) {
		return new LambdaButton(lambda);
	}

	public static Command createCommand(BooleanSupplier execute) {
		return new Command() {
			private boolean isFinished;

			@Override
			protected void execute() {
				isFinished = execute.getAsBoolean();
			}

			@Override
			protected boolean isFinished() {
				return isFinished;
			}

			@Override
			protected void initialize() {
			}

			@Override
			protected void end() {
			}

			@Override
			protected void interrupted() {
			}
		};
	}

	public static Command createCommand(Runnable initialize, BooleanSupplier execute, Runnable end,
			Runnable interrupted) {
		return new Command() {
			private boolean isFinished;

			@Override
			protected void initialize() {
				initialize.run();
			}

			@Override
			protected void execute() {
				isFinished = execute.getAsBoolean();
			}

			@Override
			protected boolean isFinished() {
				return isFinished;
			}

			@Override
			protected void end() {
				end.run();
			}

			@Override
			protected void interrupted() {
				interrupted.run();
			}
		};
	}

	public static PIDSource createPIDSource(DoubleSupplier lambda, PIDSourceType type) {
		return new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return type;
			}

			@Override
			public double pidGet() {
				return lambda.getAsDouble();
			}
		};
	}
}
