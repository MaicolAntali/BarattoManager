package com.barattoManager.exception;

/**
 * Exception that is thrown when login credentials are invalid
 */
public class InvalidCredentialsException extends Exception {

	/**
	 * Constructor of the exception
	 *
	 * @param message the detail message.
	 */
	public InvalidCredentialsException(String message) {
		super(message);
	}
}
