package com.barattoManager.exception;

/**
 * Exception used when there is no category
 */
public class NullCategoryException extends Exception {
	/**
	 * {@link NullCategoryException} constructor
	 * @param message Error message
	 */
	public NullCategoryException(String message) {
		super(message);
	}
}