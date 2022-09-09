package com.barattoManager.services.event;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface used to handle the data changes
 *
 * @param <K> value of the {@link ConcurrentHashMap}
 * @param <V> value of the {@link ConcurrentHashMap}
 */
public interface UpdateDataHandler<K, V> {

	/**
	 * Method used to add a new listener
	 *
	 * @param listener {@link UpdateDataListener}
	 */
	void addListener(UpdateDataListener<K, V> listener);

	/**
	 * Methos used to fire the event
	 *
	 * @param updatedMap {@link ConcurrentHashMap} updated
	 */
	void fireUpdateListeners(ConcurrentHashMap<K, V> updatedMap);
}
