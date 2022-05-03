package com.barattoManager.exception;

/**
 * Exception used when the password is incorrect
 */
public class PasswordNotMatch extends Exception{
	public PasswordNotMatch() {
		super();
	}

	public PasswordNotMatch(String message) {
		super(message);
	}
}
