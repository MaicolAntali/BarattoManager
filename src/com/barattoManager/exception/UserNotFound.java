package com.barattoManager.exception;

public class UserNotFound extends Exception{
	public UserNotFound() {
		super();
	}

	public UserNotFound(String message) {
		super(message);
	}
}
