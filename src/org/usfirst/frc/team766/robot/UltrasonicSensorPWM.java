package org.usfirst.frc.team766.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalOutput;

public class UltrasonicSensorPWM implements Runnable {
	private static final boolean PRINT_DATA = false;
	private static final double TIMEOUT = 2;// .0098;
	private static UltrasonicSensorPWM[] sensors = new UltrasonicSensorPWM[9];

	public static UltrasonicSensorPWM getInstance(int inputPort) {
		if (sensors[inputPort] == null) {
			sensors[inputPort] = new UltrasonicSensorPWM(inputPort);
		}
		return sensors[inputPort];
	}

	private UltrasonicSensorPWM(int port) {
		inputPort = port;
		outputPort = inputPort + 1;
		counter = new Counter();
		counter.setSemiPeriodMode(true);
		pulseController = new DigitalOutput(outputPort);
		serverThread.start();
	}

	public synchronized double getDistanceDouble() {
		return distance;
	}

	private synchronized void setValue(double d) {
		distance = d;
	}
	
	public synchronized UltrasonicValuePWM getDistance(){
		UltrasonicValuePWM returnThis = new UltrasonicValuePWM(distance,isNew);
		isNew = false;
		return returnThis;
	}

	public void setSamplesToAverage(int numberSamples) {
		counter.setSamplesToAverage(numberSamples);
	}

	public void initializeSensor() {
		counter.reset();
		pulseController.pulse(outputPort, (float) .03);
	}

	private void pr(String printData) {
		if (PRINT_DATA)
			System.out.println("Ultrasonic Sensor: " + printData);
	}

	public void run() {
		while (true) {
			initializeSensor();
			while (true) {
				double pulseLength = counter.getPeriod();
				if (pulseLength != 0) {
					setValue(pulseLength);
					isNew = true;
					break;
				} else {
					try {
						Thread.sleep(98);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
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
	private Thread serverThread = new Thread(this);
	private boolean isNew = false;
}
