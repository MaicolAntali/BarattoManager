package com.barattoManager.exception;

/**
 * Exception used when a user try to log in with invalid credentials
 */
public class InvalidCredentialsException extends Exception {
	/**
	 * {@link InvalidCredentialsException} constructor
	 * @param message Error message
	 */
	public InvalidCredentialsException(String message) {
		super(message);
	}
}
