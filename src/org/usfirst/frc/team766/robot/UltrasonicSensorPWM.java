package org.usfirst.frc.team766.robot;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class UltrasonicSensorPWM implements Runnable {
	
	private static final boolean PRINT_DATA = false;
	private static final double TIMEOUT = 10;
	private static UltrasonicSensorPWM[] sensors = new UltrasonicSensorPWM[9];

	public static UltrasonicSensorPWM getInstance(int port) {
		if (sensors == null) {
			sensors[port] = new UltrasonicSensorPWM();
		}
		return sensors[port];
	}

	private UltrasonicSensorPWM() {
		serverThread.start();
	}

	public synchronized UltrasonicValue getDistance() {
		boolean isCurrentNew = isNewValue;
		isNewValue = false;
		return new UltrasonicValue(distance,isCurrentNew);
	}
	
	public double getDistanceDouble() {
		return getDistance().distance;
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
				isNewValue = true;
			}
		}
	}
	
	public class UltrasonicValue{
		
		public UltrasonicValue(double d, boolean n){
			distance = d;
			isNew = n;
		}
		
		public double distance;
		public boolean isNew;
	}
	
	private boolean isNewValue = false;
	private SerialPort port;
	private Thread serverThread = new Thread(this);
	private double distance = Double.NaN;
}
