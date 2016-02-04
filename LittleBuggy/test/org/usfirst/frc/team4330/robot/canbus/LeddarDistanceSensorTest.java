package org.usfirst.frc.team4330.robot.canbus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.canbus.CanDevice.CANMessage;
import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor.LeddarDistanceSensorData;

import edu.wpi.first.wpilibj.can.CANMessageNotFoundException;

public class LeddarDistanceSensorTest {
	
	private LeddarDistanceSensor testObject;
	private List<CANMessage> receiveBuffer = Collections.synchronizedList(new ArrayList<CANMessage>());
	private List<CANMessage> sendBuffer = Collections.synchronizedList(new ArrayList<CANMessage>());
	
	@Before
	public void setup() {
		
		testObject = new LeddarDistanceSensor() {

			@Override
			protected CANMessage receiveData(int messageId) throws CANMessageNotFoundException {
				if ( receiveBuffer.isEmpty() ) throw new CANMessageNotFoundException();
				int messageIndex = -1;
				for ( int i = 0; i < receiveBuffer.size(); i++ ) {
					CANMessage message = receiveBuffer.get(i);
					if ( message.messageId == messageId ) {
						messageIndex = i;
						break;
					}
				}
				if ( messageIndex == -1 ) {
					throw new CANMessageNotFoundException();
				}
				CANMessage message = receiveBuffer.remove(messageIndex);
				return message;
			}

			@Override
			protected void sendData(CANMessage message) {
				sendBuffer.add(message);
			}
			
		};
		
		sendBuffer.clear();
		receiveBuffer.clear();
		
	}

	@Test
	public void testStartupAndShutdown() throws Exception {
		// verify that start and shutdown sends correct messages to device
		// and shutdown will purge any messages from the receive queue
		testObject.startUp();
		Thread.sleep(100);
		
		// send buffer should contain the message to device to startup
		Assert.assertEquals(1, sendBuffer.size());
		CANMessage message = sendBuffer.remove(0);
		Assert.assertEquals(LeddarDistanceSensor.LEDDAR_RX_BASE_ID_DEFAULT, message.messageId);
		Assert.assertEquals(LeddarDistanceSensor.COMMAND_START_SENDING_DETECTIONS, message.data);
		
		addToReceiveBuffer(testObject.getSizeMessageId(), new byte[] {0x01});
		addToReceiveBuffer(testObject.getDistanceMessageId(), new byte[] {0x3e, 0x3f, 0x11, 0x23, 0x34, 0x45, 0x78, 0x69});
		
		testObject.shutDown();
		Thread.sleep(100);
		
		Assert.assertEquals(1, sendBuffer.size());
		message = sendBuffer.remove(0);
		Assert.assertEquals(LeddarDistanceSensor.LEDDAR_RX_BASE_ID_DEFAULT, message.messageId);
		Assert.assertEquals(LeddarDistanceSensor.COMMAND_STOP_SENDING_DETECTIONS, message.data);
		
		Assert.assertEquals(0, receiveBuffer.size());
	}
	
	@Test
	public void testHappyPathMessage() throws Exception {
		testObject.startUp();
		Thread.sleep(100);
		
		// on initial startup there should be no distance measurements available
		List<LeddarDistanceSensorData> list = testObject.getDistances();
		Assert.assertEquals(true, list.isEmpty());
		
		// simulate the device sending a size message
		addToReceiveBuffer(testObject.getSizeMessageId(), new byte[] {0x03});
		
		Assert.assertEquals(true, testObject.getDistances().isEmpty());
		
		// simulate the device sending the first distance message
		// TODO verify actually little endian for two distance bytes
		// distance 30,000 stored little endian in bytes 0 and 1
		// amplitude 503 with 8 MSB in byte 2 and 4 LSB in 4 LSB of byte 3
		// sector 3 stored in 4 MSB of byte 3
		// first binary: 00110000 01110101 01111101 11000011
		// distance 65,038 stored little endian in bytes 0 and 1
		// amplitude 1022 with 8 MSB in byte 2 and 4 LSB in 4 LSB of byte 3
		// sector 15 stored in 4 MSB of byte 3
		// first binary: 00001110 11111110 11111111 10111111
		addToReceiveBuffer(testObject.getDistanceMessageId(), new byte[] {48, 117, 125, -61, 14, -2, -1, -65});
		
		Assert.assertEquals(true, testObject.getDistances().isEmpty());
		
		// distance 1 stored little endian in bytes 0 and 1
		// amplitude 1 with 8 MSB in byte 2 and 4 LSB in 4 LSB of byte 3
		// sector 1 stored in 4 MSB of byte 3
		// first binary: 00000001 00000000 00000000 01000001
		addToReceiveBuffer(testObject.getDistanceMessageId(), new byte[] {1, 0, 0, 65, 0, 0, 0, 0});
		Thread.sleep(100);
		
		list = testObject.getDistances();
		Assert.assertEquals(3, list.size());
		LeddarDistanceSensorData measurement = list.get(0);
		Assert.assertEquals(30000, measurement.getDistanceInCentimeters());
		Assert.assertEquals(3, measurement.getSegmentNumber());
		Assert.assertEquals(503, measurement.getAmplitude());
		
		measurement = list.get(1);
		Assert.assertEquals(65038, measurement.getDistanceInCentimeters());
		Assert.assertEquals(15, measurement.getSegmentNumber());
		Assert.assertEquals(1022, measurement.getAmplitude());
		
		measurement = list.get(2);
		Assert.assertEquals(1, measurement.getDistanceInCentimeters());
		Assert.assertEquals(1, measurement.getSegmentNumber());
		Assert.assertEquals(1, measurement.getAmplitude());
		
		testObject.shutDown();
		Thread.sleep(100);
	}
	
	private void addToReceiveBuffer(int messageId, byte[] data) {
		receiveBuffer.add(testObject.new CANMessage(messageId, data));
	}
}
