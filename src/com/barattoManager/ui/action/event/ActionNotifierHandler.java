package com.barattoManager.ui.action.event;

import java.util.ArrayList;

public class ActionNotifierHandler {

	private final ArrayList<ActionNotifierListener> actionNotifierListeners;

	public ActionNotifierHandler() {
		this.actionNotifierListeners = new ArrayList<>();
	}

	public void addActionNotifierListener(ActionNotifierListener listener) {
		this.actionNotifierListeners.add(listener);
	}

	public void fireActionNotifierListener(String actionName) {
		this.actionNotifierListeners.forEach(
				listener -> listener.notify(actionName)
		);
	}
}
