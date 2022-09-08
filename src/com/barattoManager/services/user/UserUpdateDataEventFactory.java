package com.barattoManager.services.user;

import com.barattoManager.services.event.UpdateDataHandler;

/**
 * Class that constructs the {@link UserUpdateDataEvent}<br/>
 * {@link UserUpdateDataEvent}is declared in the class as a static field, to ensure one instance for the whole project.
 */
public class UserUpdateDataEventFactory {

	private static final UpdateDataHandler<String, User> EVENT_HANDLER = new UserUpdateDataEvent();

	/**
	 * Method used to get the {@link UpdateDataHandler}
	 * @return EVENT_HANDLER {@link UpdateDataHandler}
	 */
	public static UpdateDataHandler<String, User> getEventHandler() {
		return EVENT_HANDLER;
	}
}
