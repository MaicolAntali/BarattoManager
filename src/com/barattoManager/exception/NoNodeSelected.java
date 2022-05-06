package com.barattoManager.exception;

/**
 * Exception used when is not selected a node
 */
public class NoNodeSelected extends Exception {
	/**
	 * {@link NoNodeSelected} constructor
	 * @param message Error message
	 */
	public NoNodeSelected(String message) {
		super(message);
	}
}
