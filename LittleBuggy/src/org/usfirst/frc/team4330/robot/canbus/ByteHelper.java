package org.usfirst.frc.team4330.robot.canbus;

public class ByteHelper {
	
	public static short readShort(byte[] data, int offset, boolean littleEndianByteOrder) {
		if(data == null) {
			throw new RuntimeException("readShort bad parameters(null byte array passed)");
		}
		
		if ( offset + 1 >= data.length ) {
			throw new RuntimeException("readShort bad parameters(index too big for arrayLength): data=" + 
				ByteHelper.bytesToHex(data) + " arrayLength=" + data.length +
				" offset=" + offset);
		}
		
		byte lo = 0;
		byte hi = 0;
		if ( littleEndianByteOrder ) {
			lo = data[offset];
			hi = data[offset + 1];
		} else {
			hi = data[offset];
			lo = data[offset + 1];
		}
		return (short) ((hi << 8) | (lo));
	}
	
	public static byte getMSBValue(byte data, int numberBits) {
		return (byte) ((data & 0xff) >> (8 - numberBits));
	}
	
	public static byte getLSBValue(byte data, int numberBits) {
		int mask = 0x00;
		for ( int i = 0; i < numberBits; i++ ) {
			mask = (mask << 1) | 0x01;
		}
		return (byte) (data & mask);
	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789abcdef".toCharArray();
		if (bytes == null) {
			return "null";
		} else {
			char[] hexChars = new char[bytes.length * 2];
			for (int j = 0; j < bytes.length; j++) {
				int v = bytes[j] & 0xFF;
				hexChars[j * 2] = hexArray[v >>> 4];
				hexChars[j * 2 + 1] = hexArray[v & 0x0F];
			}
			return new String(hexChars);
		}
	}

}
