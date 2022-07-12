package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.model.trade.Trade;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that handles events related to trade data change.</br>
 */
public class TradesChangeDataEvent implements Event<String, Trade> {

	public final ArrayList<DataChangeListener<String, Trade>> listeners;

	/**
	 * Constructor of the class.<br/>
	 * it's used to initialize listeners arraylist
	 *
	 * @see #listeners
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
