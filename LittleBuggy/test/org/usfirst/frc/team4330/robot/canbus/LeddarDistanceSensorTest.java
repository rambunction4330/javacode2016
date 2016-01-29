package org.usfirst.frc.team4330.robot.canbus;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.can.CANMessageNotFoundException;

public class LeddarDistanceSensorTest {
	
	private LeddarDistanceSensor testObject;
	private List<List<Byte>> receiveBuffer = new ArrayList<List<Byte>>();
	private List<SendInfo> sendBuffer = new ArrayList<SendInfo>();
	
	@Before
	public void setup() {
		
		testObject = new LeddarDistanceSensor() {

			@Override
			protected byte[] receiveData(int[] messageIds) throws CANMessageNotFoundException {
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
