package com.barattoManager.ui.action.event;

import java.util.ArrayList;

/**
 * Action used to handle the {@link ActionNotifierListener}
 */
public class ActionNotifierHandler {

	private final ArrayList<ActionNotifierListener> actionNotifierListeners;

	/**
	 * Constructor of the class
	 * Used to initialize the {@link ArrayList} of listeners
	 */
	public ActionNotifierHandler() {
		this.actionNotifierListeners = new ArrayList<>();
	}

	/**
	 * Method used to add a {@link ActionNotifierListener}
	 *
	 * @param listener {@link ActionNotifierListener} to add as a listener
	 */
	public void addActionNotifierListener(ActionNotifierListener listener) {
		this.actionNotifierListeners.add(listener);
	}

	/**
	 * Method used to fire a {@link ActionNotifierListener}
	 *
	 * @param actionName {@link String} that represent the action name
	 */
	public void fireActionNotifierListener(String actionName) {
		this.actionNotifierListeners.forEach(
				listener -> listener.notify(actionName)
		);
	}
}
