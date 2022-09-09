package com.barattoManager.services.meet;

import com.barattoManager.services.event.UpdateDataHandler;
import com.barattoManager.services.event.UpdateDataListener;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UpdateDataHandler used to notify the listeners that the meet manager data structure is changed
 */
public class MeetUpdateDataEvent implements UpdateDataHandler<String, Meet> {

	private final ArrayList<UpdateDataListener<String, Meet>> listeners;

	/**
	 * Constructor of {@link MeetUpdateDataEvent}
	 */
	public MeetUpdateDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(UpdateDataListener<String, Meet> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void fireUpdateListeners(ConcurrentHashMap<String, Meet> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
