package com.team2383.robot.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.strongback.Strongback;
import org.strongback.components.Switch;

public class ExclusiveSwitchGroup {
    private final HashMap<Switch, Boolean> swtchMap = new HashMap<Switch, Boolean>();

    /*
     *
     */
    public ExclusiveSwitchGroup(Switch... swtches) {
        ArrayList<Switch> swtchList = new ArrayList<Switch>(Arrays.asList(swtches));
        swtchList.forEach((swtch) -> {
            Strongback.switchReactor().onTriggered(swtch, this.getHandler(swtch));
            swtchMap.put(swtch, false);
        });
    }

    public Switch getExclusiveSwitch(Switch swtch) throws IllegalArgumentException {
        if (!swtchMap.containsKey(swtch)) throw new IllegalArgumentException("Switch not part of ExclusiveSwitchGroup");
        return () -> swtchMap.get(swtch);
    }

    /**
     * returns a {@link Rnnable} that will set the state of all of the other switches in the {@link ExclusiveSwitchGroup} to
     * false, then toggle the state of the passed swtch
     * 
     * @param swtchOn
     */
    private Runnable getHandler(Switch swtchOn) {
        return () -> {
            swtchMap.replaceAll((swtch, state) -> {
                return swtch == swtchOn ? !state : false;
            });
        };
    }
}
