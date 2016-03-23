package com.team2383.ninjaLib;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.buttons.Button;

public class OnChangeButton extends Button {
	private double last;
	private final DoubleSupplier currentSupplier;

	public OnChangeButton(DoubleSupplier currentSupplier) {
		this.currentSupplier = currentSupplier;
	}

	@Override
	public boolean get() {
		double current = currentSupplier.getAsDouble();
		boolean result = last != current;

		last = current;
		return result;
	}

}
