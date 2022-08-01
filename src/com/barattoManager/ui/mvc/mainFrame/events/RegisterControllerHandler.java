package com.barattoManager.ui.mvc.mainFrame.events;

import com.barattoManager.ui.mvc.base.BaseController;

import java.util.ArrayList;

public class RegisterControllerHandler {

	private final ArrayList<RegisterControllerListener> listeners;

	public RegisterControllerHandler() {
		listeners = new ArrayList<>();
	}

	public void addListener(RegisterControllerListener listener) {
		this.listeners.add(listener);
	}

	public void fireRegisterListeners(BaseController controller, String controllerName) {
		this.listeners.forEach(listener -> listener.register(controller, controllerName));
	}
}
