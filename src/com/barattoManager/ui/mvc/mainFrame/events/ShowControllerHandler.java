package com.barattoManager.ui.mvc.mainFrame.events;

import java.util.ArrayList;

/**
 * Class used to handle the events of {@link ShowControllerListener}
 */
public class ShowControllerHandler {

	private final ArrayList<ShowControllerListener> listeners;

	/**
	 * Constructor of the class
	 * Used to initialize the {@link ArrayList} of listeners
	 */
	public ShowControllerHandler() {
		listeners = new ArrayList<>();
	}

	/**
	 * Method used to add a {@link ShowControllerListener}
	 *
	 * @param listener {@link ShowControllerListener} to add as a listener
	 */
	public void addListener(ShowControllerListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Method used to fire a {@link ShowControllerListener}
	 *
	 * @param controllerName {@link String} that represent the controller name
	 */
	public void fireShowListeners(String controllerName) {
		this.listeners.forEach(listener -> listener.show(controllerName));
	}
}
