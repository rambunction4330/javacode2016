package org.usfirst.frc.team4330.robot.canbus;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.wpilibj.can.CANJNI;
import edu.wpi.first.wpilibj.can.CANMessageNotFoundException;

public abstract class CanDevice {
	
	protected static final int kSendMessagePeriod = 20;
	
	/**
	 * 
	 * @param messageIds
	 * @return null if the message doesn't have any data, else the data packet received
	 * @throws CANMessageNotFoundException thrown if there are no CAN messages with any of
	 * the messageIds of interest in the receive queue
	 */
	protected byte[] receiveData(int[] messageIds) throws CANMessageNotFoundException {
		
		int numberMessageIds = messageIds.length;
		ByteBuffer targetedMessageID = ByteBuffer.allocateDirect(4 * numberMessageIds);
	    targetedMessageID.order(ByteOrder.LITTLE_ENDIAN);
	    for ( int i = 0; i < numberMessageIds; i++ ) {
	    	targetedMessageID.asIntBuffer().put(i, messageIds[i]);
	    }

	    ByteBuffer timeStamp = ByteBuffer.allocateDirect(4);

	    // Get the data using full 29 bits for CAN message id mask
	    ByteBuffer dataBuffer = CANJNI.FRCNetworkCommunicationCANSessionMuxReceiveMessage(
	    	targetedMessageID.asIntBuffer(), CANJNI.CAN_MSGID_FULL_M, timeStamp);

	    if ( dataBuffer == null || dataBuffer.capacity() == 0 ) {
	    	return null;
	    } else {
	    	int size = dataBuffer.capacity();
	    	byte[] data = new byte[size];
	    	for (int i = 0; i < size; i++) {
	    		data[i] = dataBuffer.get(i);
	    	}
	    	return data;
	    }
	}
	
	/**
	 * Sends the given messageId with the given data
	 * @param messageId
	 * @param data may be null but or a byte array of max length 8 since CAN only supports
	 * 		data packets 8 bytes in length
	 */
	protected void sendData(int messageId, byte[] data) {
		ByteBuffer buffer;
		if (data != null) {
			int dataSize = data.length;
			if ( dataSize > 8 ) {
				throw new RuntimeException("sendData bad parameters(data too long): arrayLength=" +
						data.length + " but CAN protocol only support max of 8 bytes of data");
			}
			buffer = ByteBuffer.allocateDirect(dataSize);
			for (byte i = 0; i < dataSize; i++) {
				buffer.put(i, data[i]);
			}
		} else {
			buffer = null;
		}

		CANJNI.FRCNetworkCommunicationCANSessionMuxSendMessage(messageId, buffer, kSendMessagePeriod);
		System.out.println("method completed");
	}

}
