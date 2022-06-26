package com.barattoManager.manager;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ConcurrencyManager<K, V> implements Manager<ConcurrentHashMap<K,V>>{

	private final ConcurrentHashMap<K, V> dataMap;

	public ConcurrencyManager(Class<K> keyClass, Class<V> valueClass) {
		this.dataMap = readJson(keyClass, valueClass);
	}


	public ConcurrentHashMap<K, V> getDataMap() {
		return dataMap;
	}

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

