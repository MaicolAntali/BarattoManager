package com.barattoManager.exception;

/**
 * Exception used when is not selected a node
 */
public class NoNodeSelected extends Exception {
	public NoNodeSelected() {
	}

	public NoNodeSelected(String message) {
		super(message);
	}
}
