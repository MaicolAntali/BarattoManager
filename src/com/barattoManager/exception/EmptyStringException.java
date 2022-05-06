package com.barattoManager.exception;

/**
 * Exception used when a string is empty
 */
public class EmptyStringException extends Exception{
	/**
	 * {@link EmptyStringException} constructor
	 * @param message Error message
	 */
	public EmptyStringException(String message) {
		super(message);
	}
}
