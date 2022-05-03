package com.barattoManager.exception;

/**
 * Exception used when the field you are trying to create already exist
 */
public class FieldAlreadyExist extends Exception {
	public FieldAlreadyExist() {
	}

	public FieldAlreadyExist(String message) {
		super(message);
	}
}
