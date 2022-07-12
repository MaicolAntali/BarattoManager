package com.barattoManager.manager;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract class used from a concurrency manager
 * @param <K> Key
 * @param <V> Value
 */
public abstract class ConcurrencyManager<K, V> implements Manager<ConcurrentHashMap<K,V>>{

	/**
	 * {@link ConcurrentHashMap}
	 */
	private final ConcurrentHashMap<K, V> dataMap;

	/**
	 * {@link ConcurrencyManager} constructor
	 * @param keyClass Key class
	 * @param valueClass Value class
	 */
	public ConcurrencyManager(Class<K> keyClass, Class<V> valueClass) {
		this.dataMap = readJson(keyClass, valueClass);
	}


	/**
	 * Method used to get the {@link ConcurrentHashMap}
	 * @return {@link ConcurrentHashMap} of data
	 */
	public ConcurrentHashMap<K, V> getDataMap() {
		return dataMap;
	}

	/**
	 * Method used to save the data map
	 */
	public void saveDataMap() {
		try {
			getObjectMapper().writeValue(getJsonFile(), this.dataMap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		afterDataChangeActions();
	}

	/**
	 * Method used to get the json file
	 * @return Json file
	 */
	abstract File getJsonFile();

	/**
	 *  Method used to get the {@link ObjectMapper}
	 * @return {@link ObjectMapper}
	 */
	abstract ObjectMapper getObjectMapper();

	/**
	 * After data change action
	 */
	abstract void afterDataChangeActions();

	/**
	 * Method used to read a json
	 * @param keyClass Key class
	 * @param valueClass Value class
	 * @return {@link ConcurrentHashMap}
	 */
	private ConcurrentHashMap<K, V> readJson(Class<K> keyClass, Class<V> valueClass) {

		ConcurrentHashMap<K, V> data = new ConcurrentHashMap<>();

		if (getJsonFile().exists()) {
			try {
				data = getObjectMapper().readValue(
						getJsonFile(),
						getObjectMapper().getTypeFactory().constructMapType(ConcurrentHashMap.class, keyClass, valueClass));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return data;

	}
}

