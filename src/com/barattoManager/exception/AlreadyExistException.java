package com.barattoManager.exception;

/**
 * Exception used when an Object already exist.
 */
public class AlreadyExistException extends Exception {
	public AlreadyExistException(String message) {
		super(message);
	}
}
