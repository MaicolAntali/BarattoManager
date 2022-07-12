package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.model.meet.Meet;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that handles events related to meet data change.</br>
 */
public class MeetsChangeDataEvent implements Event<String, Meet> {

	public final ArrayList<DataChangeListener<String, Meet>> listeners;

	/**
	 * Constructor of the class.<br/>
	 * it's used to initialize listeners arraylist
	 *
	 * @see #listeners
	 */
	public MeetsChangeDataEvent() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addListener(DataChangeListener<String, Meet> listener) {
		listeners.add(listener);
	}

	@Override
	public void fireListener(ConcurrentHashMap<String, Meet> updatedMap) {
		listeners.forEach(listener -> listener.update(updatedMap));
	}
}
