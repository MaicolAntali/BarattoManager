package com.barattoManager.utils;

import com.barattoManager.exception.IllegalValuesException;

public class StringParse {

	private static final String ERROR_FORMAT = "Non è possibile fare il parse della stringa.";

	public static int strintToInt(String text) throws IllegalValuesException {
		try {
			return Integer.parseInt(text);
		}
		catch (NumberFormatException ex) {
			throw new IllegalValuesException(ERROR_FORMAT);
		}
	}
}
