package com.barattoManager.exception;

/**
 * Exception used when an Object already exist.
 */
public class AlreadyExistException extends Exception {

	/**
	 * {@link AlreadyExistException} constructor
	 * @param message Error message
	 */
	public AlreadyExistException(String message) {
		super(message);
	}
}
