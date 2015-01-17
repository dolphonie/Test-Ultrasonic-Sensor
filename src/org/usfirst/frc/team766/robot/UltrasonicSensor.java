package org.usfirst.frc.team766.robot;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class UltrasonicSensor implements Runnable {

	private static final boolean PRINT_DATA = false;
	private static final double TIMEOUT = 10;
	private static UltrasonicSensor us;

	public static UltrasonicSensor getInstance() {
		if (us == null) {
			us = new UltrasonicSensor();
		}
		return us;
	}

	private UltrasonicSensor() {
		serverThread.start();
	}

	public synchronized double getDistance() {
		return distance;
	}

	private synchronized void setValue(double d) {
		distance = d;
	}

	private void pr(String printData) {
		if (PRINT_DATA)
			System.out.println("Ultrasonic Sensor: " + printData);
	}

	private boolean isValid(String distance) {
		if (distance.length() != 5)
			return false;
		if (distance.charAt(0) != 'R')
			return false;
		for (int i = 1; i < distance.length(); i++) {
			char c = distance.charAt(i);
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	private String readLine() {
		StringBuffer distanceLine = new StringBuffer();
		while (true) {
			byte[] curByte = port.read(1);
			char c = (char) (curByte[0] & 0xFF);
			pr("Characer Read: " + new Character(c).toString());
			if (c == '\r') {
				return distanceLine.toString();
			}
			distanceLine.append(c);
		}
	}

	// thread so robot doesn't hang
	public void run() {
		port = new SerialPort(9600, SerialPort.Port.kOnboard, 8,
				SerialPort.Parity.kNone, SerialPort.StopBits.kOne);
		port.setTimeout(TIMEOUT);
		while (true) {
			String s = readLine();
			pr("Line Read");
			if (isValid(s)) {
				setValue(Double.parseDouble(s.substring(s.indexOf('R') + 1)));
			}
		}
	}

	private SerialPort port;
	private Thread serverThread = new Thread(this);
	private double distance = Double.NaN;
}