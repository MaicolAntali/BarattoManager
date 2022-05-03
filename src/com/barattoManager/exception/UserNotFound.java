package com.barattoManager.exception;

/**
 * Exception used when the entered user is not found
 */
public class UserNotFound extends Exception{
	public UserNotFound() {
		super();
	}

	public UserNotFound(String message) {
		super(message);
	}
}
