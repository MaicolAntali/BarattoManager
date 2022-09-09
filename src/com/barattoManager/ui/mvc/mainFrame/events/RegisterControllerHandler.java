package com.barattoManager.ui.mvc.mainFrame.events;

import com.barattoManager.ui.mvc.Controller;

import java.util.ArrayList;

/**
 * Class used to handle the events of {@link  RegisterControllerListener}
 */
public class RegisterControllerHandler {

	private final ArrayList<RegisterControllerListener> listeners;

	/**
	 * Constructor of the class
	 * Used to initialize the {@link ArrayList} of listeners
	 */
	public RegisterControllerHandler() {
		listeners = new ArrayList<>();
	}

	/**
	 * Method used to add a {@link RegisterControllerListener}
	 *
	 * @param listener {@link RegisterControllerListener} to add as a listener
	 */
	public void addListener(RegisterControllerListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Method used to fire a {@link RegisterControllerListener}
	 *
	 * @param controllerName {@link String} that represent the controller name
	 * @param controller     {@link Controller} that represent the controller
	 */
	public void fireRegisterListeners(Controller controller, String controllerName) {
		this.listeners.forEach(listener -> listener.register(controller, controllerName));
	}
}
