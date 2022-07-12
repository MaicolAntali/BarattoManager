package com.barattoManager.manager;

/**
 * Interface implemented in each Manager
 * @param <T> Type of manager
 */
public interface Manager<T> {
	/**
	 * Method used to get the data map
	 * @return data map
	 */
	T getDataMap();

	/**
	 * Method used to save the data map
	 */
	void saveDataMap();
}

