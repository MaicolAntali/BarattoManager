package com.barattoManager.ui.mvc.mainFrame.events;

public class RegisterControllerHandlerFactory {
	private static final RegisterControllerHandler REGISTER_CONTROLLER_HANDLER = new RegisterControllerHandler();

	public static RegisterControllerHandler getHandler() {
		return REGISTER_CONTROLLER_HANDLER;
	}
}
