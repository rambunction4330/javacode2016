package org.usfirst.frc.team4330.robot.canbus;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.can.CANMessageNotFoundException;

public class LeddarDistanceSensor extends CanDevice {
	
	public static final int LEDDAR_RX_BASE_ID_DEFAULT = 1856;
	public static final int LEDDAR_TX_BASE_ID_DEFAULT = 1872;
	
	private static final byte[] COMMAND_START_SENDING_DETECTIONS = new byte[] {2};
	private static final byte[] COMMAND_STOP_SENDING_DETECTIONS = new byte[] {3};
	
	private int receiveBaseMessageId = LEDDAR_RX_BASE_ID_DEFAULT;
	private int transmitBaseMessageId = LEDDAR_TX_BASE_ID_DEFAULT;
	
	// this is accessed by multiple threads and should only be read/modified within the 
	// synchronized getDistances/updateDistances methods
	private List<LeddarDistanceSensorData> distances = new ArrayList<LeddarDistanceSensorData>();
	
	// the following three receivedXXX attributes are not thread safe and should only be changed
	// by the update thread or by the startUp method prior to the update thread being started
	private int receivedSizeExpected = 0;
	private List<LeddarDistanceSensorData> receivedDistances = new ArrayList<LeddarDistanceSensorData>();
	
	// boolean values are safe to read/modify from multiple threads, so no worry about thread safety
	private boolean active = false;
	private Thread updateThread;
	
	/**
	 * Construct using default values
	 */
	public LeddarDistanceSensor() {
		
	}
	
	/**
	 * 
	 * @param receiveBaseMessageId
	 * @param transmitBaseMessageId
	 */
	public LeddarDistanceSensor(int receiveBaseMessageId, int transmitBaseMessageId) {
		this.receiveBaseMessageId = receiveBaseMessageId;
		this.transmitBaseMessageId = transmitBaseMessageId;
	}
	
	public void startUp() {
		
		if ( active ) {
			// already started, so noop
			return;
		}
	
		// initialize - it is important that the initializedReceivedState method is called
		// prior to the update thread being started
		initializeReceivedState();
		active = true;
		
		// start up thread to periodically check for received messages
		updateThread = new Thread() {

			@Override
			public void run() {
				while(active) {
					checkForMessages();
					try {
						long sleepTime = kSendMessagePeriod / 4;
						if ( sleepTime < 1 ) sleepTime = 1;
						Thread.sleep(sleepTime);
					} catch ( InterruptedException e ) {
						break;
					}
				}
			}
			
		};
		updateThread.setName("LeddarDistanceSensorReader");
		updateThread.setDaemon(true);
		updateThread.start();
		
		// tell sensor to start sending messages continuously
		sendData(transmitBaseMessageId, COMMAND_START_SENDING_DETECTIONS);
	}
	
	public void shutDown() {
		
		if ( !active ) {
			// already shutdown, so noop
			return;
		}
		
		// tell sensor to stop sending messages
		sendData(transmitBaseMessageId, COMMAND_STOP_SENDING_DETECTIONS);
		
		// set to inactive and shut down the update thread
		active = false;
		
		if ( updateThread != null ) {
			updateThread.interrupt();
		}
		
		// dump any queued received messages since we no longer care about them and
		// don't want to read stale messages if we restart later
		purgeReceivedMessages();
	}
	
	public synchronized List<LeddarDistanceSensorData> getDistances() {
		if ( active ) {
			return distances;
		} else {
			return new ArrayList<LeddarDistanceSensorData>();
		}
	}
	
	private synchronized void updateDistances(List<LeddarDistanceSensorData> updatedDistances) {
		distances = updatedDistances;
	}
	
	private synchronized void initializeReceivedState() {
		receivedDistances.clear();
		receivedSizeExpected = 0;
	}
	
	private void purgeReceivedMessages() {
		try {
			while(true) {
				// loop till a CANMessageNotFoundException occurs which means we have completed
				// purging the message queue of the received sensor messages
				pullNextSensorMessage();
			}
		} catch ( CANMessageNotFoundException e) {
			// done clearing the queue of sensor messages
		}
	}
	
	private void checkForMessages() {
		if ( !active ) {
			return;
		}
		
		try {
			// read as many messages as possible since many may be queued up
			// by looping until the CANMessageNotFoundException occurs
			while(true) {
				byte[] data = pullNextSensorMessage();
				if ( data.length == 1 ) {
					// a size message has 1 byte of data
					handleSizeMessage(data[0]);
				} else {
					// a distance message has 8 bytes of data
					handleDistanceMessage(data);
				}
			}
		} catch (CANMessageNotFoundException e) {
			// no problem since just means ran out of messages to process
		}
	}
	
	private byte[] pullNextSensorMessage() throws CANMessageNotFoundException {
		return receiveData(new int[] {receiveBaseMessageId, receiveBaseMessageId + 1});
	}
	
	private void handleSizeMessage(int size) {
		receivedSizeExpected = size;
		
		// we got a size message, so initialize our received data for distances
		receivedDistances.clear();	
	}
	
	private void handleDistanceMessage(byte[] sectorRawData) {
		if ( sectorRawData.length != 8 ) {
			throw new RuntimeException("Distance message should contain 8 bytes of data, but message was " +
				ByteHelper.bytesToHex(sectorRawData));
		}
		
		// we got a distance data packet, the format of which is 8 bytes with following pattern repeated twice
		// but if number of measurements is odd, the last data packet will contain 8 bytes but the last
		// four bytes will be zero filled.
		// pattern is: 
		// data bytes 0 and 1 contain the distance in centimeters // TODO confirm little endian since docs aren't clear
		// data byte 2 and 4 LSB of byte 3 are 12 bit value of amplitude.  This value must
		// be divided by four to get the amplitude (i.e. 2 bits for fractional part)
		// 4 MSB of byte 3 is the segment number
		
		// always read the first 4 bytes
		int firstDistance = ByteHelper.readShort(sectorRawData, 0, true);
		short firstSegmentNumber = ByteHelper.getMSBValue(sectorRawData[3], 4);
		short firstAmplitude = (short) ((sectorRawData[2] << 8) | ByteHelper.getLSBValue(sectorRawData[3], 4));
		receivedDistances.add(new LeddarDistanceSensorData(firstSegmentNumber, firstDistance, firstAmplitude));
			
		// conditionally read the second 4 bytes
		if ( receivedDistances.size() < receivedSizeExpected ) {
			int secondDistance = ByteHelper.readShort(sectorRawData, 4, true);
			short secondSegmentNumber = ByteHelper.getMSBValue(sectorRawData[7], 4);
			short secondAmplitude = (short) ((sectorRawData[6] << 8) | ByteHelper.getLSBValue(sectorRawData[7], 4));
			receivedDistances.add(new LeddarDistanceSensorData(secondSegmentNumber, secondDistance, secondAmplitude));
		}
		
		if ( receivedDistances.size() == receivedSizeExpected ) {
			// have all of the distances, so update for the client with a copy
			// of the received distances
			List<LeddarDistanceSensorData> updatedDistances = new ArrayList<LeddarDistanceSensorData>();
			updatedDistances.addAll(receivedDistances);
			updateDistances(updatedDistances);
			initializeReceivedState();
		}
	}
	
	public static final class LeddarDistanceSensorData {
		private int segmentNumber;
		private int distanceInCentimeters;
		private int amplitude;
		
		public LeddarDistanceSensorData(int segmentNumber, int distanceInCentimeters, int amplitude) {
			this.segmentNumber = segmentNumber;
			this.distanceInCentimeters = distanceInCentimeters;
			this.amplitude = amplitude;
		}

		/**
		 * The Leddar distance sensor has 16 segments
		 * @return the segment number: 0 based index with values 0-15
		 */
		public int getSegmentNumber() {
			return segmentNumber;
		}

		/**
		 * 
		 * @return the distance measurement in centimeters
		 */
		public int getDistanceInCentimeters() {
			return distanceInCentimeters;
		}

		/**
		 * 
		 * @return the strength of the signal, max value is 1024
		 */
		public int getAmplitude() {
			return amplitude;
		}
		
		
	}

}
