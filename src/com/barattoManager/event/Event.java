package com.barattoManager.event;

import com.barattoManager.event.events.DataChangeListener;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Generic interface that defines which methods should implement the events.<br/>
 * This interface will be used together with the {@link DataChangeListener} interface
 *
 * @param <K> {@link DataChangeListener} Key
 * @param <V> {@link DataChangeListener} Value
 */
public interface Event<K, V> {

	/**
	 * Method used to add a listener to the event
	 *
	 * @param listener {@link DataChangeListener} to be added to the event
	 */
	void addListener(DataChangeListener<K, V> listener);

	/**
	 * Method used to run the event in each listener<br/>
	 * In the implementation will fire the {@link DataChangeListener#update(ConcurrentHashMap)} method on each listener
	 *
	 * @param updatedMap {@link java.util.concurrent.ConcurrentHashMap} with updated data that will pass to {@link DataChangeListener#update(ConcurrentHashMap)} method
	 */
	void fireListener(ConcurrentHashMap<K, V> updatedMap);
}
