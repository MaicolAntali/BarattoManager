package com.barattoManager.ui.mvc.mainFrame.events;

import com.barattoManager.ui.mvc.Controller;

/**
 * Interface that define the {@link #register(Controller, String)} method which is used when the event to register a controller occurs
 */
public interface RegisterControllerListener {

	/**
	 * Method used to register a controller
	 *
	 * @param controller     {@link Controller}
	 * @param controllerName {@link String} controller name
	 */
	void register(Controller controller, String controllerName);
}
