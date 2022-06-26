package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;

import java.util.ArrayList;

public class ArticlesChangeDataEvent implements Event {

	public final ArrayList<DataChangeListener> listeners;

	public ArticlesChangeDataEvent() {
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