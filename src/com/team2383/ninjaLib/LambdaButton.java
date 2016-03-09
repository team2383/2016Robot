package com.team2383.ninjaLib;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.buttons.Button;

public class LambdaButton extends Button {

	private final BooleanSupplier lambda;

	public LambdaButton(BooleanSupplier lambda) {
		this.lambda = lambda;
	}

	@Override
	public boolean get() {
		return this.lambda.getAsBoolean();
	}
}
