package com.barattoManager.manager;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class Manager<K, V> {

	private final HashMap<K, V> dataMap;

	public Manager(Class<K> keyClass, Class<V> valueClass) {
		this.dataMap = readJson(keyClass, valueClass);
	}


	public HashMap<K, V> getDataMap() {
		return dataMap;
	}

	public void saveDataMap() {
		try {
			getObjectMapper().writeValue(getJsonFile(), this.dataMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	abstract File getJsonFile();
	abstract ObjectMapper getObjectMapper();

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

