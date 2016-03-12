package com.team2383.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CameraFeeds extends Subsystem {
	private class CameraUpdater extends Command {
		public CameraUpdater() {
			requires(CameraFeeds.this);
		}

		@Override
		protected void initialize() {
			CameraFeeds.this.init();
		}

		@Override
		protected void execute() {
			CameraFeeds.this.updateCam();
		}

		@Override
		protected boolean isFinished() {
			return false;
		}

		@Override
		protected void end() {
		}

		@Override
		protected void interrupted() {
		}

	}

	private final int feederCam;
	private final int shooterCam;
	private int curCam;
	private final Image frame;
	private final CameraServer server;

	public CameraFeeds() {
		// Get camera ids by supplying camera name ex 'cam0', found on roborio
		// web interface
		feederCam = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		shooterCam = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		curCam = feederCam;
		// Img that will contain camera img
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		// Server that we'll give the img to
		server = CameraServer.getInstance();
		server.setQuality(50);
	}

	public void init() {
		changeCam(feederCam);
	}

	/**
	 * Stop aka close camera stream
	 */
	public void end() {
		NIVision.IMAQdxStopAcquisition(curCam);
	}

	public void switchCam() {
		changeCam(curCam == feederCam ? shooterCam : feederCam);
	}

	/**
	 * Change the camera to get imgs from to a different one
	 *
	 * @param newId
	 *            for camera
	 */
	public void changeCam(int newId) {
		NIVision.IMAQdxStopAcquisition(curCam);
		NIVision.IMAQdxConfigureGrab(newId);
		NIVision.IMAQdxStartAcquisition(newId);
		curCam = newId;
	}

	/**
	 * Get the img from current camera and give it to the server
	 */
	public void updateCam() {
		NIVision.IMAQdxGrab(curCam, frame, 1);
		server.setImage(frame);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CameraUpdater());
	}
}