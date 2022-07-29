package com.barattoManager.ui.mvc.mainFrame;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.event.RegisterControllerListener;

import java.util.ArrayList;

public class RegisterControllerHandler {

	private final ArrayList<RegisterControllerListener> listeners;

	public RegisterControllerHandler() {
		listeners = new ArrayList<>();
	}

	public void addListener(RegisterControllerListener listener) {
		this.listeners.add(listener);
	}

	public void fireRegisterListeners(BaseController<? extends BaseModel, ? extends BaseView> controller, String controllerName) {
		this.listeners.forEach(listener -> listener.register(controller, controllerName));
	}
}
