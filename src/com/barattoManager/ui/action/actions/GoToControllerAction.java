package com.barattoManager.ui.action.actions;

import com.barattoManager.ui.action.Action;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.ControllerNames;

public class GoToControllerAction implements Action {

	private final ControllerNames controllerName;

	public GoToControllerAction(ControllerNames controllerName) {
		this.controllerName = controllerName;
	}

	@Override
	public void run() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(controllerName.toString());
	}
}
