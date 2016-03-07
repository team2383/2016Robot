package com.team2383.robot.subsystems;

import org.strongback.components.CurrentSensor;
import org.strongback.components.Motor;
import org.strongback.components.TalonSRX;
import org.strongback.function.DoubleToDoubleFunction;
import org.strongback.util.Values;

import com.team2383.robot.Constants;
import com.team2383.robot.Constants.Preset;
import com.team2383.robot.Constants.ShooterPreset;
import com.team2383.robot.components.SRXMagEncoder;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class ShooterFeeder extends Subsystem {
    public final TalonSRX shooter;
    public final Motor feeder;
    public final SRXMagEncoder shooterEncoder;
    public final SRXMagEncoder hoodEncoder;
    public final CurrentSensor feederCurrentSensor;
    public final TalonSRX hood;

    private DoubleToDoubleFunction mapHoodAngle = (x) -> x;
    private DoubleToDoubleFunction mapHoodEncoderToAngle = (x) -> x;
    private double presetShooterRPM;

    public ShooterFeeder(TalonSRX shooter, Motor feeder, TalonSRX hood, PowerDistributionPanel PDP, int feederPDPChannel) {
        super("Shooter");
        this.shooter = shooter;
        this.feeder = feeder;
        this.hood = hood;
        this.shooterEncoder = new SRXMagEncoder(shooter, CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        this.hoodEncoder = new SRXMagEncoder(hood, CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        this.feederCurrentSensor = () -> {
            return PDP.getCurrent(feederPDPChannel);
        };

        CANTalon wpiHood = hood.getWPILibCANTalon();
        wpiHood.enableBrakeMode(true);
        wpiHood.changeControlMode(TalonControlMode.Position);
        wpiHood.setPIDSourceType(PIDSourceType.kDisplacement);
        wpiHood.setPID(Constants.hoodP, Constants.hoodI, Constants.hoodD);
        wpiHood.reverseOutput(false);
        wpiHood.reverseSensor(false);
        wpiHood.setForwardSoftLimit(Constants.hoodForwardLimit);
        wpiHood.enableForwardSoftLimit(false);

        this.mapHoodAngle = Values.mapRange(0.0, 90.0).toRange(Constants.hoodReverseLimit, Constants.hoodForwardLimit);
        this.mapHoodEncoderToAngle = Values.mapRange(Constants.hoodReverseLimit, Constants.hoodForwardLimit).toRange(0.0, 90.0);

        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        wpiShooter.enableBrakeMode(false);
        wpiShooter.reverseSensor(true);
        wpiShooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        wpiShooter.changeControlMode(TalonControlMode.Speed);
        wpiShooter.setPIDSourceType(PIDSourceType.kRate);
        wpiShooter.setPID(100, 0, 40);
        wpiShooter.enable();
    }

    public void usePreset(Preset preset) {
        ShooterPreset p = preset.get();
        this.disableShooter();
        this.setHoodAngleSetpoint(p.hoodAngle);
        this.presetShooterRPM = p.shooterRPM;
    }

    public void closeAndStop() {
        usePreset(Preset.closeHoodAndStopShooter);
    }

    /**
     * set hood angle 0 degrees is closed 90 is max
     *
     * @param angle
     */
    private void setHoodAngleSetpoint(double angle) {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        useHoodPID();
        System.out.println("set hood setpoint to " + mapHoodAngle.applyAsDouble(angle));
        wpiHood.setSetpoint(mapHoodAngle.applyAsDouble(angle));
    }

    public void setHoodPower(double power) {
        hood.setSpeed(power);
    }

    public void setHoodZero() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        useHoodPID();
        hoodEncoder.zero();
        double min = hoodEncoder.getAngle();
        double max = min + 0.4; // half a rotation on the hood window motor
        System.out.println("min: " + min);
        System.out.println("max: " + max);
        this.mapHoodAngle = Values.mapRange(0.0, 90.0).toRange(min, max);
        this.mapHoodEncoderToAngle = Values.mapRange(min, max).toRange(0.0, 90.0);
        wpiHood.setForwardSoftLimit(max);
        System.out.println(min);
        wpiHood.enable();
    }

    public void setHoldHoodPosition() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        useHoodPID();
        System.out.println("set sp to" + wpiHood.getPosition());
        wpiHood.setSetpoint(wpiHood.getPosition());
        System.out.println("set sp to" + wpiHood.getSetpoint());
    }

    public double getHoodAngleSetpoint() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        useHoodPID();
        return wpiHood.getSetpoint();
    }

    public double getHoodRotations() {
        return hoodEncoder.getAngle();
    }

    public double getHoodAngle() {
        return mapHoodEncoderToAngle.applyAsDouble(hoodEncoder.getAngle());
    }

    public boolean isHoodAtSetpoint() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        return wpiHood.getError() <= Constants.hoodDegreeTolerance;
    }

    private void loadPresetShooterRPMSetpoint() {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        useShooterPID();
        wpiShooter.setSetpoint(this.presetShooterRPM);
    }

    public void setShooterPower(double power) {
        shooter.setSpeed(power);
    }

    public double getPresetShooterRPMSetpoint() {
        return this.presetShooterRPM;
    }

    public double getShooterRPM() {
        return shooterEncoder.getRPM();
    }

    public boolean isShooterAtSetpoint() {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        return wpiShooter.getError() < Constants.shooterRpmTolerance;
    }

    public void disableShooter() {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        wpiShooter.disableControl();
    }

    public void enableShooter() {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        wpiShooter.enableControl();
        loadPresetShooterRPMSetpoint();
    }

    public void setFeederPower(double power) {
        feeder.setSpeed(power);
    }

    /** Config functions **/

    private void useHoodPID() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        wpiHood.changeControlMode(TalonControlMode.Position);
    }

    private void useShooterPID() {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        wpiShooter.changeControlMode(TalonControlMode.Speed);
    }
}
