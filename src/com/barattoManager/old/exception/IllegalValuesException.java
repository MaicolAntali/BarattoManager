package com.barattoManager.old.exception;

/**
 * Exception used when a value is invalid, null or not initialized
 */
public class IllegalValuesException extends Exception {

	/**
	 * Constructor of the exception
	 *
	 * @param message the detail message.
	 */
	public IllegalValuesException(String message) {
		super(message);
	}
}
