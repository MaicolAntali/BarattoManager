package com.barattoManager.services.category;

import com.barattoManager.services.event.UpdateDataHandler;

/**
 * Class that constructs the {@link CategoryUpdateDataEvent}<br/>
 * {@link CategoryUpdateDataEvent}is declared in the class as a static field, to ensure one instance for the whole project.
 */
public class CategoryUpdateDataEventFactory {

	private static final UpdateDataHandler<String, Category> EVENT_HANDLER = new CategoryUpdateDataEvent();

	/**
	 * Method used to get the {@link UpdateDataHandler}
	 *
	 * @return EVENT_HANDLER {@link UpdateDataHandler}
	 */
	public static UpdateDataHandler<String, Category> getEventHandler() {
		return EVENT_HANDLER;
	}
}
