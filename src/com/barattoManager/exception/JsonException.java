package com.barattoManager.exception;

/**
 * Exception that is thrown when a JSON have an error
 */
public class JsonException extends Exception {

	/**
	 * Constructor of the exception
	 *
	 * @param message the detail message.
	 */
	public JsonException(String message) {
		super(message);
	}
}
