package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.model.trade.Trade;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class TradesChangeDataEvent implements Event<String, Trade> {

	public final ArrayList<DataChangeListener<String, Trade>> listeners;

	/**
	 * {@link TradesChangeDataEvent} constructor
	 */
	public TradesChangeDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(DataChangeListener<String, Trade> listener) {
		listeners.add(listener);
	}

	@Override
	public void fireListener(ConcurrentHashMap<String, Trade> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
