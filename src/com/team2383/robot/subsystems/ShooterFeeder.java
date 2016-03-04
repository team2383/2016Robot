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

    private final DoubleToDoubleFunction mapHoodAngle;
    private DoubleToDoubleFunction mapHoodEncoderToAngle;

    public ShooterFeeder(TalonSRX shooter, Motor feeder, TalonSRX hood, PowerDistributionPanel PDP, int feederPDPChannel) {
        super("Shooter");
        this.shooter = shooter;
        this.feeder = feeder;
        this.hood = hood;
        this.shooterEncoder = new SRXMagEncoder(shooter);
        this.hoodEncoder = new SRXMagEncoder(hood);
        this.feederCurrentSensor = () -> {
            return PDP.getCurrent(feederPDPChannel);
        };
        this.mapHoodAngle = Values.mapRange(0.0, 90.0).toRange(Constants.hoodAngleMin, Constants.hoodAngleMax);
        this.mapHoodEncoderToAngle = Values.mapRange(Constants.hoodAngleMin, Constants.hoodAngleMax).toRange(0.0, 90.0);

        CANTalon wpiHood = hood.getWPILibCANTalon();
        wpiHood.enableBrakeMode(true);
        wpiHood.changeControlMode(TalonControlMode.Position);
        wpiHood.setPIDSourceType(PIDSourceType.kDisplacement);
        wpiHood.setPID(0.02, 0.001, 0.02);
        wpiHood.configMaxOutputVoltage(10);
        wpiHood.reverseOutput(true);
        wpiHood.reverseSensor(true);
        wpiHood.setAllowableClosedLoopErr(Constants.hoodDegreeTolerance);
        wpiHood.enable();

        CANTalon wpiShooter = hood.getWPILibCANTalon();
        wpiShooter.enableBrakeMode(false);
        wpiShooter.setAllowableClosedLoopErr(Constants.shooterRpmTolerance);
        wpiShooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
        wpiShooter.changeControlMode(TalonControlMode.Speed);
        wpiShooter.setPIDSourceType(PIDSourceType.kRate);
        wpiShooter.setPID(10, 0, 0);
        wpiShooter.disable();

        closeAndStop();
    }

    public void usePreset(Preset preset) {
        ShooterPreset p = preset.get();
        this.disableShooter();
        this.setHoodAngleSetpoint(p.hoodAngle);
        this.setShooterRPMSetpoint(p.shooterRPM);
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
        useHoodPercentVBus();
        hood.setSpeed(power);
    }

    public void setHoldHoodPosition() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        wpiHood.setSetpoint(wpiHood.getPosition());
    }

    public double getHoodAngleSetpoint() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        useHoodPID();
        return wpiHood.getSetpoint();
    }

    public double getHoodAngle() {
        return mapHoodEncoderToAngle.applyAsDouble(hoodEncoder.getAngle());
    }

    public boolean isHoodAtSetpoint() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        return wpiHood.getError() <= Constants.hoodDegreeTolerance;
    }

    private void setShooterRPMSetpoint(double speed) {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        useShooterPID();
        wpiShooter.setSetpoint(speed);
    }

    public void setShooterPower(double power) {
        useShooterPercentVBus();
        shooter.setSpeed(power);
    }

    public double getShooterRPMSetpoint() {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        useShooterPID();
        return wpiShooter.getSetpoint();
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
        wpiShooter.disable();
    }

    public void enableShooter() {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        wpiShooter.enable();
    }

    public void setFeederPower(double power) {
        feeder.setSpeed(power);
    }

    /** Config functions **/

    private void useHoodPID() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        wpiHood.changeControlMode(TalonControlMode.Position);
    }

    private void useHoodPercentVBus() {
        CANTalon wpiHood = hood.getWPILibCANTalon();
        wpiHood.changeControlMode(TalonControlMode.PercentVbus);
    }

    private void useShooterPercentVBus() {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        wpiShooter.changeControlMode(TalonControlMode.PercentVbus);
    }

    private void useShooterPID() {
        CANTalon wpiShooter = shooter.getWPILibCANTalon();
        wpiShooter.changeControlMode(TalonControlMode.Speed);
    }
}
