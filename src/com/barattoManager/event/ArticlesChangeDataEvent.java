package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;

import java.util.ArrayList;

/**
 * Class that represent articles change data event
 */
public class ArticlesChangeDataEvent implements Event {

	/**
	 * {@link ArrayList} of listeners
	 */
	public final ArrayList<DataChangeListener> listeners;

	/**
	 * {@link ArticlesChangeDataEvent} constructor
	 */
	public ArticlesChangeDataEvent() {
		this.listeners = new ArrayList<>();
	}

	/**
	 * Method used to add the listener
	 * @param listener {@link DataChangeListener} to add
	 */
	@Override
	public void addListener(DataChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Method used to fire the listeners
	 */
	@Override
	public void fireListener() {
		for (DataChangeListener dataChangeListener : listeners) {
			dataChangeListener.update();
		}
	}
}
