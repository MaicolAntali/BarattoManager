package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;

import java.util.ArrayList;

public class TradesChangeDataEvent implements Event {

	public final ArrayList<DataChangeListener> listeners;

	public TradesChangeDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(DataChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void fireListener() {
		for (DataChangeListener dataChangeListener : listeners) {
			dataChangeListener.update();
		}
	}
}
