package com.barattoManager.services.category;

import com.barattoManager.services.event.UpdateDataHandler;

public class CategoryUpdateDataEventFactory {

	private static final UpdateDataHandler<String, Category> EVENT_HANDLER = new CategoryUpdateDataEvent();

	public static UpdateDataHandler<String, Category> getEventHandler() {
		return EVENT_HANDLER;
	}
}
