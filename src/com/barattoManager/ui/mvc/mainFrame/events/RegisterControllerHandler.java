package com.barattoManager.ui.mvc.mainFrame.events;

import com.barattoManager.ui.mvc.Controller;

import java.util.ArrayList;

/**
 * Class used to handle the events of {@link  RegisterControllerListener}
 */
public class RegisterControllerHandler {

	private final ArrayList<RegisterControllerListener> listeners;

	public RegisterControllerHandler() {
		listeners = new ArrayList<>();
	}

	public void addListener(RegisterControllerListener listener) {
		this.listeners.add(listener);
	}

	public void fireRegisterListeners(Controller controller, String controllerName) {
		this.listeners.forEach(listener -> listener.register(controller, controllerName));
	}
}
