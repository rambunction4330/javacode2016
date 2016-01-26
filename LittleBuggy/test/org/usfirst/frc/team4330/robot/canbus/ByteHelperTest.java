package org.usfirst.frc.team4330.robot.canbus;

import org.junit.Assert;
import org.junit.Test;

public class ByteHelperTest {
	
	@Test
	public void bytesToHex() {
		Assert.assertEquals("null", ByteHelper.bytesToHex(null));
		Assert.assertEquals("", ByteHelper.bytesToHex(new byte[0]));
		Assert.assertEquals("fe", ByteHelper.bytesToHex(toByteArray(new int[] {0xfe})));
		Assert.assertEquals("0abe", ByteHelper.bytesToHex(toByteArray(new int[] {0x0a, 0xbe})));
		Assert.assertEquals("0123456789abcdef", ByteHelper.bytesToHex(toByteArray(new int[] {0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef})));
	}
	
	@Test
	public void getLSBValue() {
		Assert.assertEquals(toByte(10), ByteHelper.getLSBValue(toByte(0b10101010), 4));
		Assert.assertEquals(toByte(0), ByteHelper.getLSBValue(toByte(0b10101010), 1));
		Assert.assertEquals(toByte(1), ByteHelper.getLSBValue(toByte(0b10101011), 1));
		Assert.assertEquals(toByte(3), ByteHelper.getLSBValue(toByte(0b10101011), 2));
		Assert.assertEquals(toByte(43), ByteHelper.getLSBValue(toByte(0b10101011), 7));
	}
	
	@Test
	public void getMSBValue() {
		Assert.assertEquals(toByte(0), ByteHelper.getMSBValue(toByte(0b00101011), 1));
		Assert.assertEquals(toByte(14), ByteHelper.getMSBValue(toByte(0b11101010), 4));
		Assert.assertEquals(toByte(0), ByteHelper.getMSBValue(toByte(0b00101011), 1));
		Assert.assertEquals(toByte(1), ByteHelper.getMSBValue(toByte(0b10101011), 1));
		Assert.assertEquals(toByte(2), ByteHelper.getMSBValue(toByte(0b10101011), 2));
		Assert.assertEquals(toByte(85), ByteHelper.getMSBValue(toByte(0b10101011), 7));
	}
	
	private byte[] toByteArray(int[] intArray) {
		byte[] values = new byte[intArray.length];
		for ( int i = 0; i < intArray.length; i++ ) {
			values[i] = (byte) intArray[i];
		}
		return values;
	}
	
	private byte toByte(int value) {
		return (byte) value;
	}
	

}
