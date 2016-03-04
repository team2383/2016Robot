package com.team2383.robot.subsystems;

import org.strongback.command.Requirable;

public abstract class Subsystem implements Requirable {
    private final String name;

    public Subsystem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
