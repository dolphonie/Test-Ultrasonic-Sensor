package org.usfirst.frc.team766.robot.ultrasonic;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;

public class UltrasonicSensorPWM {//Do not use! Use UltrasonicPWM reader instead
	private static final boolean PRINT_DATA = false;

	public UltrasonicSensorPWM(int port) {
		inputPort = port;
		outputPort = inputPort + 1;
		counter = new Counter(port);
		counter.setSemiPeriodMode(true);
		pulseController = new DigitalOutput(outputPort);
		pulseController.set(false);
		getDistance();
	}

	public double getDistance() {
		initializeSensor();
		double pulseLength = counter.getPeriod()*1e6;
		pr("Pulse Length: " + pulseLength);
		return pulseLength;
	}

	public void initializeSensor() {
		counter.reset();
		pulseController.set(true);
		Timer.delay(.03);
		pulseController.set(false);
		Timer.delay(.15);
	}

	private void pr(String printData) {
		if (PRINT_DATA)
			System.out.println("Ultrasonic Sensor: " + printData);
	}

	public void free() {
		pulseController.free();
		counter.free();
	}

	private int inputPort, outputPort;
	private DigitalOutput pulseController;
	private Counter counter;
}
