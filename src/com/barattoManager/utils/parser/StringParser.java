package com.barattoManager.utils.parser;

import com.barattoManager.exception.IllegalValuesException;

public final class StringParser {

	private static final String ERROR_FORMAT = "Non è possibile fare il parse della stringa.";

	public static int stringIntoInteger(String text) throws IllegalValuesException {
		try {
			return Integer.parseInt(text);
		}
		catch (NumberFormatException ex) {
			throw new IllegalValuesException(ERROR_FORMAT);
		}
	}
}
