package com.team2383.ninjaLib;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.buttons.Button;

public class OnChangeButton extends Button {
	private double last;
	private final double threshold;
	private final DoubleSupplier currentSupplier;

	public OnChangeButton(DoubleSupplier currentSupplier, double threshold) {
		this.currentSupplier = currentSupplier;
		this.threshold = threshold;
	}

	public OnChangeButton(DoubleSupplier currentSupplier) {
		this.currentSupplier = currentSupplier;
		this.threshold = 0;
	}

	@Override
	public boolean get() {
		double current = currentSupplier.getAsDouble();
		boolean result = Math.abs(last - current) < threshold;

		last = current;
		return result;
	}

}
