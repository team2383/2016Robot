package com.team2383.robot.components;

import org.strongback.Strongback;
import org.strongback.components.Switch;

public class ToggleSwitch implements Switch, Runnable {
    private boolean state = false;

    public ToggleSwitch(Switch swtch) {
        Strongback.switchReactor().onTriggered(swtch, this);
    }

    public ToggleSwitch(Switch swtch, boolean initialState) {
        Strongback.switchReactor().onTriggered(swtch, this);
        state = initialState;
    }

    @Override
    public boolean isTriggered() {
        return state;
    }

    @Override
    public void run() {
        state = !state;
    }
}
