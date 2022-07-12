package com.barattoManager.utils.parser;

import com.barattoManager.exception.IllegalValuesException;

/**
 * String Parser (Utility class)
 */
public final class StringParser {

	/**
	 * Error: Impossible to do the parse of the string
	 */
	private static final String ERROR_FORMAT = "Non è possibile fare il parse della stringa.";

	/**
	 * Method used to convert a string into an int
	 * @param text String to parse
	 * @return Parsed number
	 * @throws IllegalValuesException Is thrown if is impossible to do the parse of the string
	 */
	public static int stringToInt(String text) throws IllegalValuesException {
		try {
			return Integer.parseInt(text);
		}
		catch (NumberFormatException ex) {
			throw new IllegalValuesException(ERROR_FORMAT);
		}
	}
}
