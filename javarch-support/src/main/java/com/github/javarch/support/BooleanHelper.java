package com.github.javarch.support;

public class BooleanHelper {


	public static final String[] TRUE_VALUES = { "1", "t", "true", "y", "yes", "on", "checked" };
	public static final String[] FALSE_VALUES = { "0", "f", "false", "n", "no", "off", "unchecked" };

	public static boolean toBoolean(String value) {
		return toBoolean(value, false);
	}

	public static boolean toBoolean(String value, boolean defaultValue) {

		if (value == null) {

			return defaultValue;
		}
		else {

			if (isTrueToken(value)) {
				return true;
			}
			else if (isFalseToken(value)) {
				return false;
			}
			else {
				return defaultValue;
			}

		}

	}

	public static boolean isBooleanToken(String value) {

		return isTrueToken(value) || isFalseToken(value);
	}

	public static boolean isFalseToken(String value) {

		boolean flag = false;

		if (value != null) {
			String trimmedValue = value.trim();
			flag = (trimmedValue.equalsIgnoreCase(FALSE_VALUES[0]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[1]) ||
					trimmedValue.equalsIgnoreCase(FALSE_VALUES[2]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[3]) ||
					trimmedValue.equalsIgnoreCase(FALSE_VALUES[3]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[4]) ||
					trimmedValue.equalsIgnoreCase(FALSE_VALUES[5]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[6]));
		}

		return flag;
	}

	public static boolean isTrueToken(String value) {

		boolean flag = false;

		if (value != null) {
			String trimmedValue = value.trim();
			flag = (trimmedValue.equalsIgnoreCase(TRUE_VALUES[0]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[1]) ||
					trimmedValue.equalsIgnoreCase(TRUE_VALUES[2]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[3]) ||
					trimmedValue.equalsIgnoreCase(TRUE_VALUES[3]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[4]) ||
					trimmedValue.equalsIgnoreCase(TRUE_VALUES[5]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[6]));
		}

		return flag;
	}
}
