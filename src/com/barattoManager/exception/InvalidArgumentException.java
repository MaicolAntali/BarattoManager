package com.barattoManager.exception;

/**
 * Exception used when a value is invalid, null or not initialized
 */
public class InvalidArgumentException extends Exception {

	/**
	 * Constructor of the exception
	 *
	 * @param message the detail message.
	 */
	public InvalidArgumentException(String message) {
		super(message);
	}
}
