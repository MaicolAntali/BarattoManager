package com.barattoManager.services.meet;

import com.barattoManager.services.event.UpdateDataHandler;

public class MeetUpdateDataEventFactory {

	private static final UpdateDataHandler<String, Meet> EVENT_HANDLER = new MeetUpdateDataEvent();

	public static UpdateDataHandler<String, Meet> getEventHandler() {
		return EVENT_HANDLER;
	}
}
