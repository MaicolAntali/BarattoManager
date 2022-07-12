package com.barattoManager.event.events;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Generic interface which handles the data change event.<br/>
 * Each time the {@link #update(ConcurrentHashMap)} method is invoked it means that the data of some {@link ConcurrentHashMap} has been updated.
 *
 * @param <K> {@link ConcurrentHashMap} Key
 * @param <V> {@link ConcurrentHashMap} Value
 */
public interface DataChangeListener<K, V> {

	/**
	 * This method is invoked whenever the data of a {@link ConcurrentHashMap} changes.
	 *
	 * @param updatedMap {@link ConcurrentHashMap} with updated data
	 */
	void update(ConcurrentHashMap<K, V> updatedMap);
}