package com.barattoManager.services.event;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface used to notify a data change
 *
 * @param <K> Key of the {@link ConcurrentHashMap}
 * @param <V> Value of the {@link ConcurrentHashMap}
 */
public interface UpdateDataListener<K, V> {

	/**
	 * Method invoke when data change
	 *
	 * @param updatedMap {@link ConcurrentHashMap} with the updated data
	 */
	void update(ConcurrentHashMap<K, V> updatedMap);
}
