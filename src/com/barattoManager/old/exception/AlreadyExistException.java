package com.barattoManager.old.exception;

/**
 * Exception that is thrown when a system object already exists
 */
public class AlreadyExistException extends Exception {

	/**
	 * Constructor of the exception
	 *
	 * @param message the detail message.
	 */
	public AlreadyExistException(String message) {
		super(message);
	}
}
