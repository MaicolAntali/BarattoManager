package com.barattoManager.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;


/**
 * This class is a <b>Singleton Class</b> used to access from anywhere to app settings.
 * This class reads a file called config.json placed in /resources/json.
 */
public class AppConfigurator {

	private static AppConfigurator instance;

	private final ObjectNode node;

	private AppConfigurator() {
		try {
			InputStream configFile = getClass().getResourceAsStream("/json/config.json");
			node = new ObjectMapper().readValue(configFile, ObjectNode.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method used to get the {@link AppConfigurator} instance.
	 *
	 * @return The Instance of {@link AppConfigurator}
	 */
	public static AppConfigurator getInstance() {
		if (instance == null)
			instance = new AppConfigurator();

		return instance;
	}

	/**
	 * Method used to get application data as a {@link String}
	 *
	 * @param nodeName Name of the node
	 * @return {@link String}
	 */
	public String getAppDataAsText(String nodeName) {
		return node.get("app").get(nodeName).asText();
	}

	/**
	 * Method used to get file name as a {@link String}
	 *
	 * @param nodeName Name of the node
	 * @return {@link String}
	 */
	public String getFileName(String nodeName) {
		return node.get("file").get(nodeName).asText();
	}

	/**
	 * Method used to get password settings as a {@link String}
	 *
	 * @param nodeName Name of the node
	 * @return {@link String}
	 */
	public String getPasswordSetting(String nodeName) {
		return node.get("password").get(nodeName).asText();
	}

	/**
	 * Method used to get default categories as a {@link JsonNode}
	 *
	 * @return {@link JsonNode}
	 */
	public JsonNode getDefaultField() {
		return node.get("default_category");
	}

}