package org.usfirst.frc.team766.robot;

import edu.wpi.first.wpilibj.Counter;

public class UltrasonicSensorPWM {

	private static final boolean PRINT_DATA = false;
	private static final double TIMEOUT = 10;
	private static UltrasonicSensorPWM[] sensors = new UltrasonicSensorPWM[9];

	public static UltrasonicSensorPWM getInstance(int channel) {
		if (sensors == null) {
			sensors[channel] = new UltrasonicSensorPWM(channel);
		}
		return sensors[channel];
	}

	private UltrasonicSensorPWM(int channel) {
		counter = new Counter();
		counter.setSemiPeriodMode(false);
	}

	public void setSamplesToAverage(int numberSamples) {
		counter.setSamplesToAverage(numberSamples);
	}

	public synchronized double getDistance() {
		double curValue = counter.getPeriod();
		if (curValue == 0) {
			return Double.NaN;
		}
		return curValue;
	}

	private void pr(String printData) {
		if (PRINT_DATA)
			System.out.println("Ultrasonic Sensor: " + printData);
	}

	// private boolean isValid(String distance) {
	// if (distance.length() != 5)
	// return false;
	// if (distance.charAt(0) != 'R')
	// return false;
	// for (int i = 1; i < distance.length(); i++) {
	// char c = distance.charAt(i);
	// if (!Character.isDigit(c))
	// return false;
	// }
	// return true;
	// }
	//
	// private String readLine() {
	// StringBuffer distanceLine = new StringBuffer();
	// while (true) {
	// byte[] curByte = port.read(1);
	// char c = (char) (curByte[0] & 0xFF);
	// pr("Characer Read: " + new Character(c).toString());
	// if (c == '\r') {
	// return distanceLine.toString();
	// }
	// distanceLine.append(c);
	// }
	// }

	// thread so robot doesn't hang

	
	private Counter counter;
	private double distance = Double.NaN;
}
