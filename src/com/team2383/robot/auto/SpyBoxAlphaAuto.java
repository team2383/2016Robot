/* Created Sun Feb 14 19:02:04 EST 2016 */
package com.team2383.robot.auto;

import org.strongback.command.CommandGroup;
import org.strongback.control.Controller;


import com.kauailabs.navx.frc.AHRS;
import com.team2383.robot.Robot;
import com.team2383.robot.auto.commands.Drive;
import com.team2383.robot.auto.commands.Hood;
import com.team2383.robot.auto.commands.RotateToRelativeAngle;
import com.team2383.robot.auto.commands.Shoot;

public class SpyBoxAlphaAuto extends CommandGroup{
	/*
	 * calls autonomous commands sequentially and simultaneously
	 * 
	 * This auto starts in the spy box, moves to shoot, and goes back and forth through the low bar
	 * 
	 * Points: 20
	 */
	 
	
	final AHRS ahrs;
	public SpyBoxAlphaAuto(Robot robot) {
		Controller controller = (Controller)robot.turnController.withTolerance(2.0).enable();
		ahrs = robot.ahrs;
        /*
         * 1) Move Hood to optimum angle
         * 2) Shoot
         * 3) Drive Forward
         * 4) turn toward low bar
         * 5) drive past low bar
         * 6) move backward through low bar
         * 7) go back through low bar into neutral zone
         * 8) turn so that our feeder is facing drivers
         */
		
		sequentially(new Hood(1.0,1.0),				
        			 new Shoot(1),
        			 new Drive(controller, () -> controller.withTarget(0), Robot.drive,12));
        
		simultaneously(new RotateToRelativeAngle(controller,-90, Robot.drive),
        			   new Hood(-1.0,1.0));
		
        sequentially(new Drive(controller, () -> controller.withTarget(-90) , Robot.drive, 120),
        			 new Drive(controller, () -> controller.withTarget(-90) , Robot.drive, -72),
        			 new Drive(controller, () -> controller.withTarget(-90) , Robot.drive, 96),
        			 new RotateToRelativeAngle(controller, 180 ,Robot.drive)
        			 );
    }
}
