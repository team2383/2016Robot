/* Created Mon Feb 15 13:05:56 EST 2016 */
package com.team2383.robot.auto;

import org.strongback.command.CommandGroup;
import org.strongback.control.Controller;


import com.kauailabs.navx.frc.AHRS;
import com.team2383.robot.Robot;
import com.team2383.robot.auto.commands.Drive;
import com.team2383.robot.auto.commands.Hood;
import com.team2383.robot.auto.commands.RotateToRelativeAngle;
import com.team2383.robot.auto.commands.Shoot;

public class LowBarSimpleAuto extends CommandGroup{
	/*
	 * calls autonomous commands sequentially and simultaneously
	 * 
	 * This auto starts in front of the low bar, goes through and positions itself and shoots. Tries to repeat itself, maybe terminated in middle
	 * penalty where bumper goes over midline may deduct 5 points from maximum of 30
	 * 
	 * NOTE: may be not worth attempting second goal while getting the penalty if auto time terminates before second shot is attempted
	 * 
	 * Points: min-> 20 , with penalty -> 25 ,  max -> 30
	 */
	
	
	final AHRS ahrs;
	public LowBarSimpleAuto(Robot robot) {
		Controller controller = (Controller)robot.turnController.withTolerance(2.0).enable();
		ahrs = robot.ahrs;
        /*
         * 1) Drive forward through low bar
         * 2) turn angle
         * 3) move hood
         * 4) shoot
         * 5) lower hood
         * 6) straighten angle
         * 7) go back through low bar into neutral zone
         * 8) straight back and feed
         * 9) reset angle
         * 10) repeat steps 1-4 if possible
         */
		
		sequentially(new Drive(controller, () -> controller.withTarget(0), Robot.drive,48),
        			 new RotateToRelativeAngle(controller, -30, Robot.drive),
        			 new Hood(1.0,1.0),
        			 new Shoot(1));
		
        simultaneously(new RotateToRelativeAngle(controller, 30, Robot.drive),
        			   new Hood(-1.0,1.0));
        
        sequentially(new Drive(controller, () -> controller.withTarget(30), Robot.drive, -60),
        			 new RotateToRelativeAngle(controller, 0, Robot.drive),
        			 new Drive(controller, () -> controller.withTarget(0), Robot.drive, 60),
        			 new RotateToRelativeAngle(controller, -30, Robot.drive),
        			 new Hood(1.0,1.0),
        			 new Shoot(1)
        			 );
    }
}
