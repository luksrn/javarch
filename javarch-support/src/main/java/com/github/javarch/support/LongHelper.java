package com.github.javarch.support;

public class LongHelper {
	
	public static long toLong(String value) {
		return toLong(value, 0);
	}

	public static long toLong(String value, long defaultValue) {

		long valueAsLong = 0;

		try {
			valueAsLong = Long.parseLong(value);
		} catch (NumberFormatException e) {			
		}

		return valueAsLong;
	}
}
