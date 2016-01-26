package com.team2383.components;

import java.util.function.DoubleSupplier;

import org.strongback.annotation.ThreadSafe;
import org.strongback.components.DistanceSensor;
import org.strongback.components.Gyroscope;
import org.strongback.components.SpeedSensor;
import org.strongback.function.DoubleToDoubleFunction;

/**
 * A speedometer is a device that measures the instantaneous linear speed of a
 * device in inches per second. Convenience constructors are provided to convert
 * one or more {@link Gyroscope} to a {@link Speedometer}.
 *
 * @author Matthew Alonso
 *
 */

@ThreadSafe
public interface Speedometer extends SpeedSensor {
	/**
	 * Gets the current speed of this {@link Speedometer} in in/s
	 *
	 * @return the value of this {@link Speedometer}
	 * @see #getSpeedInFeetPerSecond()
	 */
	public double getSpeedInInchesPerSecond();

	/**
	 * Gets the current value of this {@link DistanceSensor} in ft/s.
	 *
	 * @return the value of this {@link DistanceSensor}
	 * @see #getDistanceInInches()
	 */
	default public double getSpeedInFeetPerSecond() {
		return getSpeedInInchesPerSecond() / 12.0;
	};

	/**
	 * Gets the current speed of this {@link Speedometer}. Default unit is ft/s
	 *
	 * @return the value of this {@link Speedometer}
	 * @see #getSpeedInFeetPerSecond()
	 */
	@Override
	default public double getSpeed() {
		return getSpeedInFeetPerSecond();
	}

	/**
	 * Create a speedometer for the given functions that return angular
	 * velocity, and a function to convert angular velocity into linear speed in
	 * in/s.
	 *
	 * @param scale
	 *            constant scaling factor that turns the angular velocity to
	 *            linear speed in inches per second.
	 * @param angularVelocity
	 *            the function(s) that returns the angular velocity; may not be
	 *            null
	 * @return the speedometer
	 */
	public static Speedometer create(DoubleToDoubleFunction scaleFunction, DoubleSupplier... angularVelocitySuppliers) {
		return () -> {
			double speed = 0;

			for (final DoubleSupplier angularVelocitySupplier : angularVelocitySuppliers) {
				speed += angularVelocitySupplier.getAsDouble();
			}

			speed /= angularVelocitySuppliers.length;
			speed = scaleFunction.applyAsDouble(speed);

			return speed;
		};
	}
}
