package com.barattoManager.ui.mvc.mainFrame.events;

/**
 * Class used to create a {@link ShowControllerHandler}
 */
public class ShowControllerHandlerFactory {
	private static final ShowControllerHandler SHOW_CONTROLLER_HANDLER = new ShowControllerHandler();

	/**
	 * Method used to get a register controller handler
	 *
	 * @return {@link ShowControllerHandler}
	 */
	public static ShowControllerHandler getHandler() {
		return SHOW_CONTROLLER_HANDLER;
	}
}
