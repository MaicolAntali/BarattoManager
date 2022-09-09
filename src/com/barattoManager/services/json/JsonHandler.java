package com.barattoManager.services.json;

import com.barattoManager.services.event.UpdateDataListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Generic class to handle JSON files<br/>
 * The main purposes of the class are:
 * <ul>
 *     <li>
 *         Read JSON files and return a {@link ConcurrentHashMap} with all values or, if the file does not exist, an empty {@link ConcurrentHashMap}
 *     </li>
 *     <li>
 *         Thanks to the implementation of the {@link UpdateDataListener}, save the data every time the data is updated.<br/>
 *     </li>
 * </ul>
 *
 * @param <K> {@link UpdateDataListener} and {@link ConcurrentHashMap} Key
 * @param <V> {@link UpdateDataListener} and {@link ConcurrentHashMap} Value
 */
public class JsonHandler<K, V> implements UpdateDataListener<K, V> {

	private final File jsonFile;
	private final ObjectMapper objectMapper;

	/**
	 * Constructor of the class
	 *
	 * @param jsonFilePath The path of the JSON file
	 */
	public JsonHandler(String jsonFilePath) {
		this.jsonFile = new File(jsonFilePath);
		this.objectMapper = new ObjectMapper();
	}

	/**
	 * Constructor of the class
	 *
	 * @param jsonFilePath The path of the JSON file
	 * @param objectMapper Customised implementation of {@link ObjectMapper}
	 */
	public JsonHandler(String jsonFilePath, ObjectMapper objectMapper) {
		this.jsonFile = new File(jsonFilePath);
		this.objectMapper = objectMapper;
	}

	/**
	 * Method used by read the JSON file declared in the constructor<br/>
	 * If JSON file does not exist it returns an empty {@link ConcurrentHashMap}
	 *
	 * @param keyClass   {@link Class} that represents the key of the {@link ConcurrentHashMap}
	 * @param valueClass {@link Class} that represents the key of the {@link ConcurrentHashMap}
	 * @return A {@link ConcurrentHashMap} with all the values read by the JSON otherwise an empty {@link ConcurrentHashMap}.
	 */
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
