package com.barattoManager.old.exception;

/**
 * Exception that is thrown when no node is selected in a {@link javax.swing.JTree}
 */
public class NoNodeSelected extends Exception {

	/**
	 * Constructor of the exception
	 *
	 * @param message the detail message.
	 */
	public NoNodeSelected(String message) {
		super(message);
	}
}
