package org.smpp.pdu;

import static org.junit.Assert.*;
import static org.powermock.reflect.Whitebox.*;
import static org.smpp.pdu.Matchers.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class ByteDataTest {
	private static Class<?> CLAZZ = ByteData.class;

	@Test
	public void testStringEqualZero() throws Exception {
		checkString("", 0);
	}
	@Test
	public void testStringNonZero() throws Exception {
		checkString(" ", 1);
	}
	@Test
	public void testStringOneZero() throws Exception {
		assertTrue(stringLengthException(0, 0, 1).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString(" ", 0); } )));
	}
	@Test
	public void testStringLowerBound() throws Exception {
		checkString(" ", 1, 2);
	}
	@Test
	public void testStringLower0Bound() throws Exception {
		checkString("", 0, 1);
	}
	@Test
	public void testStringUpperBound() throws Exception {
		checkString("AB", 1, 2);
	}
	@Test
	public void testStringUpper0Bound() throws Exception {
		checkString("", 0, 0);
	}
	@Test
	public void testStringBelow1Bound() throws Exception {
		assertTrue(stringLengthException(1, 2, 0).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString("", 1, 2); } )));
	}
	@Test
	public void testStringBelow2Bound() throws Exception {
		assertTrue(stringLengthException(2, 3, 1).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString("A", 2, 3); } )));
	}
	@Test
	public void testStringAboveBound() throws Exception {
		assertTrue(stringLengthException(1, 2, 3).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString("ABC", 1, 2); } )));
	}
	@Test
	public void testStringLenLowerBound() throws Exception {
		checkString(1, 1, 2);
	}
	@Test
	public void testStringLenLower0Bound() throws Exception {
		checkString(0, 0, 1);
	}
	@Test
	public void testStringLenUpperBound() throws Exception {
		checkString(1, 2, 2);
	}
	@Test
	public void testLenUpper0Bound() throws Exception {
		checkString(0, 0, 0);
	}
	@Test
	public void testStringLenBelow1Bound() throws Exception {
		assertTrue(stringLengthException(1, 2, 0).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString(1, 0, 2); } )));
	}
	@Test
	public void testStringLenBelow2Bound() throws Exception {
		assertTrue(stringLengthException(2, 3, 1).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString(2, 1, 3); } )));
	}
	@Test
	public void testStringLenAboveBound() throws Exception {
		assertTrue(stringLengthException(1, 2, 3).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString(1, 3, 2); } )));
	}
	@Test
	public void testStringUTF16() throws Exception {
		assertTrue(stringLengthException(0, 1, 4).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString(" ", 1, "UTF-16"); })));
	}
	@Test
	public void testStringInvalidEncoding() throws Exception {
		assertThrows(UnsupportedEncodingException.class, 
				() -> { checkString(" ", 1, "UTF-17"); });
	}
	
	@Test
	public void testNullCString() throws Exception {
		assertTrue(stringLengthException(0, 0, 1).matches(
				assertThrows(WrongLengthOfStringException.class,
						() -> { checkCString((String) null, 0, 0); })));
	}
	@Test
	public void testCStringEqualZero() throws Exception {
		checkCString("", 1);
	}
	@Test
	public void testCStringNonZero() throws Exception {
		checkCString(" ", 2);
	}
	@Test
	public void testCStringOneZero() throws Exception {
		assertTrue(stringLengthException(1, 1, 2).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkCString(" ", 1); } )));
	}
	@Test
	public void testCStringLowerBound() throws Exception {
		checkCString(" ", 2, 3);
	}
	@Test
	public void testCStringLower0Bound() throws Exception {
		checkCString("", 1, 2);
	}
	@Test
	public void testCStringUpperBound() throws Exception {
		checkCString("AB", 2, 3);
	}
	@Test
	public void testCStringUpper0Bound() throws Exception {
		checkCString("", 1, 1);
	}
	@Test
	public void testCStringBelow1Bound() throws Exception {
		assertTrue(stringLengthException(2, 3, 1).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkCString("", 2, 3); } )));
	}
	@Test
	public void testCStringBelow2Bound() throws Exception {
		assertTrue(stringLengthException(3, 4, 2).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkCString("A", 3, 4); } )));
	}
	@Test
	public void testCStringAboveBound() throws Exception {
		assertTrue(stringLengthException(2, 3, 4).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkCString("ABC", 2, 3); } )));
	}
	@Test
	public void testCStringLenLowerBound() throws Exception {
		checkString(1, 1, 2);
	}
	@Test
	public void testCStringLenLower0Bound() throws Exception {
		checkString(0, 0, 1);
	}
	@Test
	public void testCStringLenUpperBound() throws Exception {
		checkString(1, 2, 2);
	}
	@Test
	public void testCStringLenUpper0Bound() throws Exception {
		checkString(0, 0, 0);
	}
	@Test
	public void testCheckCStringLenBelow1Bound() throws Exception {
		assertTrue(stringLengthException(1, 2, 0).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString(1, 0, 2); } )));
	}
	@Test
	public void testCheckCStringLenBelow2Bound() throws Exception {
		assertTrue(stringLengthException(2, 3, 1).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString(2, 1, 3); } )));
	}
	@Test
	public void testCheckCStringLenAboveBound() throws Exception {
		assertTrue(stringLengthException(1, 2, 3).matches(
				assertThrows(WrongLengthOfStringException.class, 
						() -> { checkString(1, 3, 2); } )));
	}

	
	@Test
	public void testCheckRange() throws Exception {
		checkRange(0, 0, 0);
		checkRange(0, 10, 100);
	}
	@Test
	public void testCheckRangeBelow() throws Exception {
		assertTrue(integerOutOfRange(5, 10, 1).matches(
				assertThrows(IntegerOutOfRangeException.class,
						() -> { checkRange(5, 1, 10); })));
	}
	@Test
	public void testCheckRangeAbove() throws Exception {
		assertTrue(integerOutOfRange(5, 10, 11).matches(
				assertThrows(IntegerOutOfRangeException.class,
						() -> { checkRange(5, 11, 10); })));
	}

	@Test
	public void testDecodeUnsignedByte() throws Exception {
		assertEquals(0, decodeUnsigned((byte) 0x00));
		assertEquals(127, decodeUnsigned((byte) 0x7f));
		assertEquals(255, decodeUnsigned((byte) 0xff));
	}
	@Test
	public void testDecodeUnsignedShort() throws Exception {
		assertEquals(0, decodeUnsigned((short) 0));
		assertEquals(32768, decodeUnsigned((short) 32768));
	}
	@Test
	public void testEncodeUnsignedShort() throws Exception {
		assertEquals((byte) 0x00, encodeUnsigned((short) 0));
		assertEquals((byte) 0xff, encodeUnsigned((short) 255));
	}
	@Test
	public void testEncodeUnsignedInt() throws Exception {
		assertEquals((short) 0, encodeUnsigned((int) 0));
		assertEquals((short) 32768, encodeUnsigned((int) 32768));
	}

	@Test
	public void testNullDate() throws Exception {
		checkDate(null);
	}
	@Test
	public void testEmptyDate() throws Exception {
		checkDate("");
	}
	@Test
	public void testOneShortDate() throws Exception {
		assertThrows(WrongDateFormatException.class, 
				() -> { checkDate("123456789012345"); });
	}
	@Test
	public void testOneLongDate() throws Exception {
		assertThrows(WrongDateFormatException.class, 
				() -> { checkDate("12345678901234567"); });
	}
	@Test
	public void testLocTimeWrongRel() throws Exception {
		assertThrows(WrongDateFormatException.class, 
				() -> { checkDate("000000000012345S"); });
	}
	@Test
	public void testLocTimeRel() throws Exception {
		checkDate("000000000012345R");
	}
	@Test
	public void testLocTimeNegRel() throws Exception {
		checkDate("-00000000001234R");
	}
	@Test
	public void testLocTimeMinus() throws Exception {
		checkDate("230511235901300-");
	}
	@Test
	public void testLocTimePlus() throws Exception {
		checkDate("230511235901300+");
	}
	@Test
	public void testLocTimeDiffMax() throws Exception {
		checkDate("230511235901348+");
	}
	public void testLocTimeWrongSeconds() throws Exception {
		assertThrows(WrongDateFormatException.class, 
				() -> { checkDate("230511235960000-"); });
	}
	@Test
	public void testLocTimeDiffTooHigh() throws Exception {
		assertThrows(WrongDateFormatException.class, 
				() -> { checkDate("230511235901349-"); });
	}
	
	// maps to ByteData static methods

	private void checkString(String string, int max) throws Exception {
		invokeMethod(CLAZZ, "checkString", string, max);
	}
	private void checkString(String string, int min, int max) throws Exception {
		invokeMethod(CLAZZ, "checkString", string, min, max);
	}
	private void checkString(String string, int max, String encoding) throws Exception {
		invokeMethod(CLAZZ, "checkString", string, max, encoding);
	}
	private void checkString(int min, int length, int max) throws Exception {
		invokeMethod(CLAZZ, "checkString", min, length, max);
	}
	private void checkCString(String string, int max) throws Exception {
		invokeMethod(CLAZZ, "checkCString", string, max);
	}
	private void checkCString(String string, int min, int max) throws Exception {
		invokeMethod(CLAZZ, "checkCString", string, min, max);
	}
	private void checkDate(String string) throws Exception {
		invokeMethod(CLAZZ, "checkDate", string);
	}
	private void checkRange(int min, int value, int max) throws Exception {
		invokeMethod(CLAZZ, "checkRange", min, value, max);
	}
	private short decodeUnsigned(byte bite) throws Exception {
		return Whitebox.<Short> invokeMethod(CLAZZ, "decodeUnsigned", bite);
	}
	private int decodeUnsigned(short value) throws Exception {
		return Whitebox.<Integer> invokeMethod(CLAZZ, "decodeUnsigned", value);
	}
	private byte encodeUnsigned(short value) throws Exception {
		return Whitebox.<Byte> invokeMethod(CLAZZ, "encodeUnsigned", value);
	}
	private short encodeUnsigned(int positive) throws Exception {
		return Whitebox.<Short> invokeMethod(CLAZZ, "encodeUnsigned", positive);
	}
}
