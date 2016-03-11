package com.team2383.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.team2383.robot.commands.CameraStreamer;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DualUSBCamera extends Subsystem {
	public final int feederCam;
	public final int boardCam;
	public int curCam;
	private final Image frame;

	public DualUSBCamera() {
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		feederCam = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		boardCam = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		curCam = feederCam;
		CameraServer.getInstance().setQuality(50);
		changeCam(feederCam);
	}

	/**
	 * Stop aka close camera stream
	 */
	public void end() {
		NIVision.IMAQdxStopAcquisition(curCam);
	}

	public void switchCam() {
		changeCam(curCam == feederCam ? boardCam : feederCam);
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
		try {
			NIVision.IMAQdxGrab(curCam, frame, 1);
			CameraServer.getInstance().setImage(frame);
		} catch (Exception e) {
			System.out.println("Camera error, most likely camera not found.");
			e.printStackTrace();
		}
	}

	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new CameraStreamer());
	}
}