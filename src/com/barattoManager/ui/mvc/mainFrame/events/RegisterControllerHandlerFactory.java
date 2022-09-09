package com.barattoManager.ui.mvc.mainFrame.events;

/**
 * Class used to create a {@link RegisterControllerHandler}
 */
public class RegisterControllerHandlerFactory {
	private static final RegisterControllerHandler REGISTER_CONTROLLER_HANDLER = new RegisterControllerHandler();

	/**
	 * Method used to get a register controller handler
	 *
	 * @return {@link RegisterControllerHandler}
	 */
	public static RegisterControllerHandler getHandler() {
		return REGISTER_CONTROLLER_HANDLER;
	}
}
