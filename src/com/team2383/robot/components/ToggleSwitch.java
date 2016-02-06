package com.team2383.robot.components;

import java.util.ArrayList;
import java.util.Arrays;

import org.strongback.annotation.ThreadSafe;
import org.strongback.components.Switch;

/**
 * toggle switch, wraps normal switch so it toggles
 *
 * @author Matthew Alonso
 */
@ThreadSafe
public class ToggleSwitch implements Switch {
	private boolean lastState;
	private final ArrayList<Switch> switches;

	/**
	 * Checks if this switch is triggered.
	 *
	 * @return {@code true} if this switch was triggered, or {@code false}
	 *         otherwise
	 */
	@Override
	public boolean isTriggered() {
		if (switches.stream().map((swtch) -> swtch.isTriggered()).reduce((triggered, current) -> triggered || current)
				.get()) {
			this.lastState = !this.lastState;
			return this.lastState;
		}
		return this.lastState;
	}

	public ToggleSwitch(Switch... switches) {
		this.switches = new ArrayList<Switch>(Arrays.asList(switches));
	}
}