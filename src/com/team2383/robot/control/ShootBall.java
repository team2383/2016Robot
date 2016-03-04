package com.team2383.robot.control;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

import com.team2383.robot.Constants;
import com.team2383.robot.subsystems.ShooterFeeder;

public class ShootBall extends CommandGroup {
    private final ShooterFeeder shooterFeeder;

    /**
     * Feed in ball until the average amperage draw of the feeder motor has increased by the
     * {@link Constants.Feeder.currentMultiplier}
     *
     * then push the ball back at {@link Constants.Feeder.pushAwayPower} for {@link pushAwayLengthInSeconds}
     *
     * @param shooterFeeder
     * @param config
     */
    public ShootBall(ShooterFeeder shooterFeeder) {
        super();
        this.shooterFeeder = shooterFeeder;
        sequentially(
                new WaitForSetpoints(shooterFeeder),
                new Shoot(shooterFeeder));
    }

    @Override
    public void interrupted() {
        this.end();
    }

    @Override
    public void end() {
        shooterFeeder.setFeederPower(0);
    }

    private class WaitForSetpoints extends Command {
        private final ShooterFeeder shooterFeeder;

        public WaitForSetpoints(ShooterFeeder shooterFeeder) {
            super(shooterFeeder);
            this.shooterFeeder = shooterFeeder;
            shooterFeeder.enableShooter();
        }

        @Override
        public boolean execute() {
            return shooterFeeder.isShooterAtSetpoint() && shooterFeeder.isHoodAtSetpoint();
        }

        @Override
        public void interrupted() {
            shooterFeeder.disableShooter();
        }
    }

    private class Shoot extends Command {
        private final ShooterFeeder shooterFeeder;

        public Shoot(ShooterFeeder shooterFeeder) {
            super(Constants.shooterFollowThruTime, shooterFeeder);
            this.shooterFeeder = shooterFeeder;
        }

        @Override
        public boolean execute() {
            shooterFeeder.setFeederPower(Constants.shooterFeederKickPower);
            return false;
        }

        @Override
        public void interrupted() {
            shooterFeeder.setFeederPower(0);
            shooterFeeder.disableShooter();
        }
    }
}
