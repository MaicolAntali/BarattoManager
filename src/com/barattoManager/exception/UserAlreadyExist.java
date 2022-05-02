package com.barattoManager.exception;

/**
 * Exception used when the user you are trying to register is already registered
 */
public class UserAlreadyExist extends Exception{
	public UserAlreadyExist() {
		super();
	}

	public UserAlreadyExist(String message) {
		super(message);
	}
}
