package com.team2383.robot.components;

import org.strongback.components.Gyroscope;
import org.strongback.components.TalonSRX;

import edu.wpi.first.wpilibj.CANTalon;

public class SRXMagEncoder implements Gyroscope {
    private final CANTalon talon;

    public SRXMagEncoder(TalonSRX talonSB) {
        this.talon = talonSB.getWPILibCANTalon();

        talon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
    }

    @Override
    public double getAngle() {
        return talon.getPosition();
    }

    @Override
    public Gyroscope zero() {
        // TODO Auto-generated method stub
        talon.setPosition(talon.getPulseWidthPosition());
        return this;
    }

    public double getRPM() {
        return talon.getSpeed();
    }

    @Override
    public double getRate() {
        return talon.getSpeed() * 6;
    }

}
