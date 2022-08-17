package com.barattoManager.services.trade;

import com.barattoManager.services.event.UpdateDataHandler;

public class TradeUpdateDataEventFactory {

	private static final UpdateDataHandler<String, Trade> EVENT_HANDLER = new TradeUpdateDataEvent();

	public static UpdateDataHandler<String, Trade> getEventHandler() {
		return EVENT_HANDLER;
	}
}
