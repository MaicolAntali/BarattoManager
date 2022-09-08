package com.barattoManager.services.trade;

import com.barattoManager.services.event.UpdateDataHandler;

/**
 * Class that constructs the {@link TradeUpdateDataEvent}<br/>
 * {@link TradeUpdateDataEvent}is declared in the class as a static field, to ensure one instance for the whole project.
 */
public class TradeUpdateDataEventFactory {

	private static final UpdateDataHandler<String, Trade> EVENT_HANDLER = new TradeUpdateDataEvent();

	/**
	 * Method used to get the {@link UpdateDataHandler}
	 * @return EVENT_HANDLER {@link UpdateDataHandler}
	 */
	public static UpdateDataHandler<String, Trade> getEventHandler() {
		return EVENT_HANDLER;
	}
}
