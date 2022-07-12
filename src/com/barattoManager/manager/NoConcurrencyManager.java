package com.barattoManager.manager;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Abstract class used from a no concurrency manager
 * @param <K> Key
 * @param <V> Value
 */
public abstract class NoConcurrencyManager<K, V> implements Manager<HashMap<K,V>>{

	/**
	 * {@link HashMap}
	 */
	private final HashMap<K, V> dataMap;

	/**
	 * {@link NoConcurrencyManager} constructor
	 * @param keyClass Key class
	 * @param valueClass Value class
	 */
	public NoConcurrencyManager(Class<K> keyClass, Class<V> valueClass) {
		this.dataMap = readJson(keyClass, valueClass);
	}

	/**
	 * Method used to get the {@link HashMap}
	 * @return data {@link HashMap}
	 */
	public HashMap<K, V> getDataMap() {
		return dataMap;
	}

	/**
	 * Method used to save the data {@link HashMap}
	 */
	public void saveDataMap() {
		try {
			getObjectMapper().writeValue(getJsonFile(), this.dataMap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		afterDataChangeActions();
	}

	abstract File getJsonFile();
	abstract ObjectMapper getObjectMapper();
	abstract void afterDataChangeActions();

	private HashMap<K, V> readJson(Class<K> keyClass, Class<V> valueClass) {

		HashMap<K, V> data = new HashMap<>();

		if (getJsonFile().exists()) {
			try {
				data = getObjectMapper().readValue(
						getJsonFile(),
						getObjectMapper().getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return data;
	}
}

