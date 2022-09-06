package com.barattoManager.ui.action.actions;

import com.barattoManager.ui.action.Action;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.ControllerNames;

public class RegisterShowControllerAction implements Action {

	private final ControllerNames controllerName;
	private final Controller controller;

	public RegisterShowControllerAction(ControllerNames controllerName, Controller controller) {
		this.controllerName = controllerName;
		this.controller = controller;
	}

	@Override
	public void run() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				controller,
				controllerName.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(controllerName.toString());
	}
}
