package org.usfirst.frc.team766.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;

public class UltrasonicSensorPWM {
	private static final boolean PRINT_DATA = false;
	private static final double TIMEOUT = 2;// .0098;

	public UltrasonicSensorPWM(int port) {
		inputPort = port;
		outputPort = inputPort + 1;
		counter = new Counter(port);
		counter.setSemiPeriodMode(true);
		pulseController = new DigitalOutput(outputPort);
	}

	public synchronized double getDistanceDouble() {
		return distance;
	}

	private synchronized void setValue(double d) {
		distance = d;
	}

	public synchronized UltrasonicValuePWM getDistance() {
		UltrasonicValuePWM returnThis = new UltrasonicValuePWM(distance, isNew);
		isNew = false;
		return returnThis;
	}

	public void setSamplesToAverage(int numberSamples) {
		counter.setSamplesToAverage(numberSamples);
	}

	public void initializeSensor() {
		counter.reset();
		pulseController.set(true);
		Timer.delay(.03);
		pulseController.set(false);
		Timer.delay(.2);
	}

	private void pr(String printData) {
		if (PRINT_DATA)
			System.out.println("Ultrasonic Sensor: " + printData);
	}

	public void updateValue() {
		initializeSensor();
		double pulseLength = counter.getPeriod();
		pr("Pulse Length: " + pulseLength);
		setValue(pulseLength * 1e6);
		isNew = true;
	}

	public class UltrasonicValuePWM {

		public UltrasonicValuePWM(double d, boolean n) {
			distance = d;
			isNew = n;
		}

		public double distance;
		public boolean isNew;
	}

	private int inputPort, outputPort;
	private DigitalOutput pulseController;
	private Counter counter;
	private double distance = Double.NaN;
	private boolean isNew = false;
}
