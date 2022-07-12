package com.barattoManager.exception;

/**
 * Exception that is thrown when a category does not exist or is null
 */
public class NullCategoryException extends Exception {

	/**
	 * Constructor of the exception
	 *
	 * @param message the detail message.
	 */
	public NullCategoryException(String message) {
		super(message);
	}
}
