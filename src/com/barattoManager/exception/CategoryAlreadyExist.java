package com.barattoManager.exception;

/**
 * Exception used when the category you are trying to create already exist
 */
public class CategoryAlreadyExist extends Exception {
	public CategoryAlreadyExist() {
	}

	public CategoryAlreadyExist(String message) {
		super(message);
	}
}
