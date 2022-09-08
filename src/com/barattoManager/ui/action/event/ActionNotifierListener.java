package com.barattoManager.ui.action.event;

public interface ActionNotifierListener {

	/**
	 * Method used to notify that an action has been executed
	 *
	 * @param actionName {@link String} that represent the action name
	 */
	void notify(String actionName);

}
