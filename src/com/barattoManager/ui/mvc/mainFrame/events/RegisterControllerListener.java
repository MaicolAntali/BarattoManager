package com.barattoManager.ui.mvc.mainFrame.events;

import com.barattoManager.ui.mvc.Controller;

/**
 * Interface that define the {@link #register(Controller, String)} method which is used when the event to register a controller occurs
 */
public interface RegisterControllerListener {

	void register(Controller controller, String controllerName);
}
