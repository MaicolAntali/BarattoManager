package com.barattoManager.exception;

/**
 * Exception that is thrown when login credentials are invalid
 */
public class InvalidCredentialException extends Exception {

	/**
	 * Constructor of the exception
	 *
	 * @param message the detail message.
	 */
	public InvalidCredentialException(String message) {
		super(message);
	}
}
