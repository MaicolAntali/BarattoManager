package com.barattoManager.services.trade;

import com.barattoManager.services.event.UpdateDataHandler;
import com.barattoManager.services.event.UpdateDataListener;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UpdateDataHandler used to notify the listeners that the trade manager data structure is changed
 */
public class TradeUpdateDataEvent implements UpdateDataHandler<String, Trade> {

	private final ArrayList<UpdateDataListener<String, Trade>> listeners;

	public TradeUpdateDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(UpdateDataListener<String, Trade> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void fireUpdateListeners(ConcurrentHashMap<String, Trade> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
