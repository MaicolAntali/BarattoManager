package com.barattoManager.services.meet;

import com.barattoManager.services.event.UpdateDataHandler;

/**
 * Class that constructs the {@link MeetUpdateDataEvent}<br/>
 * {@link MeetUpdateDataEvent}is declared in the class as a static field, to ensure one instance for the whole project.
 */
public class MeetUpdateDataEventFactory {

	private static final UpdateDataHandler<String, Meet> EVENT_HANDLER = new MeetUpdateDataEvent();

	/**
	 * Method used to get the {@link UpdateDataHandler}
	 * @return EVENT_HANDLER {@link UpdateDataHandler}
	 */
	public static UpdateDataHandler<String, Meet> getEventHandler() {
		return EVENT_HANDLER;
	}
}
