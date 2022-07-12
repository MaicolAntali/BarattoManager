package com.barattoManager.utils.parser;

import com.barattoManager.exception.IllegalValuesException;

/**
 * Utility class used to convert a string to an integer
 */
public final class StringParser {

	private static final String ERROR_FORMAT = "Non è possibile fare il parse della stringa.";

	/**
	 * Method used to convert a string to an integer
	 * @param text  string to convert to integer
	 * @return an integer
	 * @throws IllegalValuesException Is thrown if it is impossible to parse the string to integer
	 */
	public static int stringIntoInteger(String text) throws IllegalValuesException {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException ex) {
			throw new IllegalValuesException(ERROR_FORMAT);
		}
	}
}
