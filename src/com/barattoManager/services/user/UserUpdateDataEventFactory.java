package com.barattoManager.services.user;

import com.barattoManager.services.event.UpdateDataHandler;

public class UserUpdateDataEventFactory {

	private static final UpdateDataHandler<String, User> EVENT_HANDLER = new UserUpdateDataEvent();

	public static UpdateDataHandler<String, User> getEventHandler() {
		return EVENT_HANDLER;
	}
}
