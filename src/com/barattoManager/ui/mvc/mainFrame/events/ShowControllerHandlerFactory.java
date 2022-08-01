package com.barattoManager.ui.mvc.mainFrame.events;

public class ShowControllerHandlerFactory {
	private static final ShowControllerHandler SHOW_CONTROLLER_HANDLER = new ShowControllerHandler();

	public static ShowControllerHandler getHandler() {
		return SHOW_CONTROLLER_HANDLER;
	}
}
