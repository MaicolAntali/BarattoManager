package com.barattoManager.exception;

/**
 * Exception used when a string is empty
 */
public class IllegalValuesException extends Exception{
	/**
	 * {@link IllegalValuesException} constructor
	 * @param message Error message
	 */
	public IllegalValuesException(String message) {
		super(message);
	}
}
