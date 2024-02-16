package com.fm.school.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScannerUtilTest {

	@Test
	public void testGetIntInputValid() {
		String input = "123\n";
		System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
		int result = ScannerUtil.getIntInput("Enter an integer: ");
		assertEquals(123, result);
	}

	@Test
	public void testGetIntInputInvalid() {
		String input = "abc\n123\n";
		System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
		System.setOut(new java.io.PrintStream(out));
		int result = ScannerUtil.getIntInput("Enter an integer: ");
		assertEquals(123, result);
		assertTrue(out.toString().contains("Invalid input. Please enter an integer."));
	}

	@Test
	public void testGetStringInput() {
		String input = "Hello World\n";
		System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
		String result = ScannerUtil.getStringInput("Enter a string: ");
		assertEquals("Hello World", result);
	}
}
