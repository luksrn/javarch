package com.github.javarch.support;

public class IntegerHelper {
	
	public static int toInteger(String value) {
		return toInteger(value, 0);
	}

	public static int toInteger(String value, int defaultValue) {

		int valueAsInt = 0;

		try {
			valueAsInt = Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
		}

		return valueAsInt;
	}
}
