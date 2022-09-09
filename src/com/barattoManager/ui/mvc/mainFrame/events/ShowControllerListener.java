package com.barattoManager.ui.mvc.mainFrame.events;

import com.barattoManager.ui.mvc.Controller;

/**
 * Interface that define the {@link #show(String)} method which is used when the event to show a controller occurs
 */
public interface ShowControllerListener {

	/**
	 * Method used to show a controller
	 * @param controllerName {@link String} controller name
	 */
	void show(String controllerName);
}
