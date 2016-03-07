package com.team2383.robot.control;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.team2383.robot.Constants;
import com.team2383.robot.Constants.Preset;
import com.team2383.robot.subsystems.ShooterFeeder;

public class FeedInBall extends CommandGroup {
    /**
     * Feed in ball until the average amperage draw of the feeder motor has increased by the
     * {@link Constants.Feeder.currentMultiplier}
     *
     * then push the ball back at {@link Constants.Feeder.pushAwayPower} for {@link pushAwayLengthInSeconds}
     *
     * @param shooterFeeder
     * @param config
     */
    public FeedInBall(ShooterFeeder shooterFeeder) {
        super();
        sequentially(
                new IntakeUntilCurrentJump(shooterFeeder),
                new PushAway(shooterFeeder));
    }

    private class IntakeUntilCurrentJump extends Command {
        private final ShooterFeeder shooterFeeder;

        private double avgAmperage;

        public IntakeUntilCurrentJump(ShooterFeeder shooterFeeder) {
            super(shooterFeeder);
            this.shooterFeeder = shooterFeeder;
            shooterFeeder.usePreset(Preset.feeding);
            shooterFeeder.enableShooter();
        }

        @Override
        public boolean execute() {
            shooterFeeder.setFeederPower(Constants.feedPower);
            shooterFeeder.setShooterPower(Constants.feedShooterPower);
            if (shooterFeeder.feederCurrentSensor.getCurrent() >= avgAmperage * Constants.feedCurrentMultiplier)
                return true;
            else {
                avgAmperage += shooterFeeder.feederCurrentSensor.getCurrent();
                avgAmperage /= 2;
                return false;
            }
        }

        @Override
        public void interrupted() {
            shooterFeeder.usePreset(Preset.closeHoodAndStopShooter);
            this.end();
        }

        @Override
        public void end() {
            shooterFeeder.setFeederPower(0);
            shooterFeeder.setShooterPower(0);
            shooterFeeder.disableShooter();
        }
    }

    private class PushAway extends Command {
        private final ShooterFeeder shooterFeeder;

        public PushAway(ShooterFeeder shooterFeeder) {
            super(Constants.feedPushAwayLengthInSeconds, shooterFeeder);
            this.shooterFeeder = shooterFeeder;
        }

        @Override
        public boolean execute() {
            shooterFeeder.setFeederPower(Constants.feedPushAwayPower);
            return false;
        }

        @Override
        public void interrupted() {
            this.end();
        }

        @Override
        public void end() {
            shooterFeeder.setFeederPower(0);
            shooterFeeder.setShooterPower(0);
            shooterFeeder.disableShooter();
        }
    }
}
