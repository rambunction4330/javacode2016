package org.usfirst.frc.team4330.robot.canbus;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team4330.robot.canbus.LeddarDistanceSensor.LeddarDistanceSensorData;

import edu.wpi.first.wpilibj.can.CANMessageNotFoundException;

public class LeddarDistanceSensorTest {
	
	private LeddarDistanceSensor testObject;
	private List<List<Byte>> receiveBuffer = new ArrayList<List<Byte>>();
	private List<SendInfo> sendBuffer = new ArrayList<SendInfo>();
	
	@Before
	public void setup() {
		
		testObject = new LeddarDistanceSensor() {

			@Override
			protected byte[] receiveData(int messageId) throws CANMessageNotFoundException {
				if ( receiveBuffer.isEmpty() ) throw new CANMessageNotFoundException();
				List<Byte> message = receiveBuffer.remove(0);
				byte[] result = new byte[message.size()];
				for ( int i = 0; i < message.size(); i++ ) {
					result[i] = message.get(i);
				}
				return result;
			}

			@Override
			protected void sendData(int messageId, byte[] data) {
				sendBuffer.add(new SendInfo(messageId, data));
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
		SendInfo info = sendBuffer.remove(0);
		Assert.assertEquals(LeddarDistanceSensor.LEDDAR_RX_BASE_ID_DEFAULT, info.messageId);
		Assert.assertEquals(LeddarDistanceSensor.COMMAND_START_SENDING_DETECTIONS, info.data);
		
		addToReceiveBuffer(new byte[] {0x01});
		addToReceiveBuffer(new byte[] {0x3e, 0x3f, 0x11, 0x23, 0x34, 0x45, 0x78, 0x69});
		
		testObject.shutDown();
		Thread.sleep(100);
		
		Assert.assertEquals(1, sendBuffer.size());
		info = sendBuffer.remove(0);
		Assert.assertEquals(LeddarDistanceSensor.LEDDAR_RX_BASE_ID_DEFAULT, info.messageId);
		Assert.assertEquals(LeddarDistanceSensor.COMMAND_STOP_SENDING_DETECTIONS, info.data);
		
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
		addToReceiveBuffer(new byte[] {0x03});
		Assert.assertEquals(true, testObject.getDistances().isEmpty());
		
		// simulate the device sending the first distance message
		// TODO verify actually little endian for two distance bytes
		// distance 30,000 stored little endian in bytes 0 and 1
		// amplitude 503 with 8 MSB in byte 2 and 4 LSB in 4 LSB of byte 3
		// sector 3 stored in 4 MSB of byte 3
		// first binary: 00110000 01110101 01111101 00111100
		// distance 65,038 stored little endian in bytes 0 and 1
		// amplitude 1022 with 8 MSB in byte 2 and 4 LSB in 4 LSB of byte 3
		// sector 15 stored in 4 MSB of byte 3
		// first binary: 00001110 11111110 11111111 11111011
		addToReceiveBuffer(new byte[] {48, 117, 125, 60, 14, -2, -1, -5});
		
		// distance 1 stored little endian in bytes 0 and 1
		// amplitude 1 with 8 MSB in byte 2 and 4 LSB in 4 LSB of byte 3
		// sector 1 stored in 4 MSB of byte 3
		// first binary: 00000001 00000000 00000000 00010100
		addToReceiveBuffer(new byte[] {1, 0, 0, 20, 0, 0, 0, 0});
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
	
	public class SendInfo {
		int messageId;
		byte[] data;
		public SendInfo(int messageId, byte[] data) {
			this.messageId = messageId;
			this.data = data;
		}
	}
	
	private void addToReceiveBuffer(byte[] data) {
		List<Byte> list = new ArrayList<Byte>();
		for ( int i = 0; i < data.length; i++ ) {
			list.add(data[i]);
		}
		receiveBuffer.add(list);
	}
}
