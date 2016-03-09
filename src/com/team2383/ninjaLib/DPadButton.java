package com.team2383.ninjaLib;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class DPadButton extends Button {
	private final Joystick joystick;
	private final Direction direction;

	public DPadButton(Joystick joystick, Direction direction) {
		this.joystick = joystick;
		this.direction = direction;
	}

	// NOTE: Doesn't support multiple directions
	@Override
	public boolean get() {
		int degree = joystick.getPOV(0);

		return degree == direction.degree;
	}

	public enum Direction {
		UP(0), DOWN(180), LEFT(270), RIGHT(90);

		int degree;

		Direction(int degree) {
			this.degree = degree;
		}
	}
}