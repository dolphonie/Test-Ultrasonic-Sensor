package org.usfirst.frc.team766.robot.ultrasonic;

import org.usfirst.frc.team766.robot.ultrasonic.UltrasonicSensorPWM;

import edu.wpi.first.wpilibj.command.Command;

public class UltrasonicPWMReader implements Runnable {
	public static UltrasonicPWMReader instance;

	public static UltrasonicPWMReader getInstance() {
		if (instance == null) {
			instance = new UltrasonicPWMReader();
		}
		return instance;
	}

	public static double calculateAngle(double d1, double d2) {
		return Math.atan((0.5 * (Math.abs(d1 - d2))) / 152.4);
	}

	private UltrasonicPWMReader() {
		sensor1 = new UltrasonicSensorPWM(8);
		sensor2 = new UltrasonicSensorPWM(6);
		dataCollector = new Thread(this);
		dataCollector.start();
	}

	private void pr(String line) {
		System.out.println("UltrasonicPWMReader: " + line);
	}

	public synchronized double[] getDistanceDouble() {
		return sensorValues;
	}

	public synchronized UltrasonicInfo getDistance() {
		boolean n = isNew;
		isNew = false;
		double angle = calculateAngle(sensorValues[0],sensorValues[1]);
		return new UltrasonicInfo(sensorValues, angle, n);
	}

	private synchronized void setDistance(double d1, double d2) {
		sensorValues[0] = d1;
		sensorValues[1] = d2;
	}

	public void run() {
		while (true) {
			setDistance(sensor1.getDistance(), sensor2.getDistance());
			isNew = true;
		}
	}

	public void free() {
		sensor1.free();
		sensor2.free();
		dataCollector.interrupt();
	}

	private double[] sensorValues = { Double.NaN, Double.NaN };
	private boolean isNew = false;
	private Thread dataCollector;
	private UltrasonicSensorPWM sensor1, sensor2;
}
