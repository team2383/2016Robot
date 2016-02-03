package com.team2383.robot.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.strongback.command.Requirable;
import org.strongback.components.Relay;
import org.strongback.components.Solenoid;

public class Shifter implements Relay, Requirable {
    private final List<Solenoid>                                  solenoids;

    private static final EnumMap<Solenoid.Direction, Relay.State> STATE_MAP = new EnumMap<Solenoid.Direction, Relay.State>(
            Solenoid.Direction.class);

    static {
        STATE_MAP.put(Solenoid.Direction.EXTENDING, Relay.State.ON);
        STATE_MAP.put(Solenoid.Direction.RETRACTING, Relay.State.OFF);
        STATE_MAP.put(Solenoid.Direction.STOPPED, Relay.State.OFF);
    };

    public Shifter(Solenoid... solenoids) {
        this.solenoids = new ArrayList<Solenoid>(Arrays.asList(solenoids));
    }

    @Override
    public State state() {
        return STATE_MAP.get(solenoids.get(0).getDirection());
    }

    @Override
    public Relay on() {
        solenoids.forEach((solenoid) -> solenoid.extend());
        return this;
    }

    @Override
    public Relay off() {
        solenoids.forEach((solenoid) -> solenoid.retract());
        return this;
    }
}
