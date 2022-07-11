package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;

/**
 * Interface that represent an Event
 */
public interface Event {
	/**
	 * Method used to add the listener
	 * @param listener {@link DataChangeListener} to add
	 */
	void addListener(DataChangeListener listener);

	/**
	 * Method used to fire the listener
	 */
	void fireListener();
}
