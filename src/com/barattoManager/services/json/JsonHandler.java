package com.barattoManager.services.json;

import com.barattoManager.services.event.UpdateDataListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class JsonHandler<K, V> implements UpdateDataListener<K, V> {

	private final File jsonFile;
	private final ObjectMapper objectMapper;

	public JsonHandler(String jsonFilePath) {
		this.jsonFile = new File(jsonFilePath);
		this.objectMapper = new ObjectMapper();
	}

	public JsonHandler(String jsonFilePath, ObjectMapper objectMapper) {
		this.jsonFile = new File(jsonFilePath);
		this.objectMapper = objectMapper;
	}

	public ConcurrentHashMap<K, V> readJson(Class<K> keyClass, Class<V> valueClass) {

		ConcurrentHashMap<K, V> data = new ConcurrentHashMap<>();

		if (this.jsonFile.exists()) {
			try {
				data = this.objectMapper.readValue(
						this.jsonFile,
						this.objectMapper.getTypeFactory().constructMapType(ConcurrentHashMap.class, keyClass, valueClass));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return data;
	}

	@Override
	public void update(ConcurrentHashMap<K, V> updatedMap) {
		try {
			this.objectMapper.writeValue(this.jsonFile, updatedMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
