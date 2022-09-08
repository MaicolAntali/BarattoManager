package com.barattoManager.exception;

/**
 * Exception that is thrown when an object does not exist or is null
 */
public class NullObjectException extends Exception {

	/**
	 * Constructor of the exception
	 *
	 * @param message the detail message
	 */
	public NullObjectException(String message) {
		super(message);
	}
}
