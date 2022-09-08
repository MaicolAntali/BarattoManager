package com.barattoManager.ui.action.actions;

import com.barattoManager.ui.action.Action;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.ControllerNames;

/**
 * Action used to show a controller in the mainframe
 */
public class GoToControllerAction implements Action {

	private final ControllerNames controllerName;

	/**
	 * Constructor of the class
	 *
	 * @param controllerName used to show the controller
	 */
	public GoToControllerAction(ControllerNames controllerName) {
		this.controllerName = controllerName;
	}

	@Override
	public void run() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(controllerName.toString());
	}
}
