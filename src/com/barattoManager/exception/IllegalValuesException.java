package com.barattoManager.exception;

/**
 * Exception used when an input contains non-acceptable values
 */
public class IllegalValuesException extends Exception{
	/**
	 * {@link IllegalValuesException} constructor
	 * @param message Error message
	 */
	public IllegalValuesException(String message) {
		super(message);
	}
}
