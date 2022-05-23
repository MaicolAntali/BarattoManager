package com.barattoManager.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to app settings.
 * This class reads a file called config.json placed in /resources/json.
 */
public final class AppConfigurator {

	/**
	 * {@link ObjectNode} that contains the JSON node
	 */
	private final ObjectNode node;

	/**
	 * {@link AppConfigurator} constructor
	 */
	private AppConfigurator() {
		try {
			InputStream configFile = getClass().getResourceAsStream("/json/config.json");
			node = new ObjectMapper().readValue(configFile, ObjectNode.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Holder class of instance
	 */
	private static final class AppConfiguratorHolder {
		/**
		 * Instance of {@link AppConfigurator}
		 */
		private static final AppConfigurator instance = new AppConfigurator();

	}

    /**
     * Method used to create get the {@link AppConfigurator} instance.
     * This method uses the lazy loading mechanism cause the inner class is loaded only if
     * the {@code getInstance()} method is called.
     * Also is thread safe cause every thread read the same {@link AppConfigurator} instance.
     * @return The Instance of {@link AppConfigurator} class
     */
    public static AppConfigurator getInstance() {
        return AppConfiguratorHolder.instance;
    }

	/**
	 * Method used to get application data as a {@link String}
	 * @param nodeName Name of the data to retrieve.
	 * @return {@link String} that represent the value
	 */
	public String getAppDataAsText(String nodeName) {
		return node.get("app").get(nodeName).asText();
	}

	/**
	 * Method used to get file name as a {@link String}
	 * @param nodeName Name of the data to retrieve.
	 * @return {@link String} that represent the value
	 */
	public String getFileName(String nodeName) {
		return node.get("file").get(nodeName).asText();
	}

	/**
	 * Method used to get password settings as a {@link String}
	 * @param nodeName Name of the data to retrieve.
	 * @return {@link String} that represent the value
	 */
	public String getPasswordSetting(String nodeName) {
		return node.get("password").get(nodeName).asText();
	}

	/**
	 * Method used to get default categories as a {@link JsonNode}
	 * @return {@link JsonNode} that represent the array of default categories
	 */
	public JsonNode getDefaultField() {
		return node.get("default_category");
	}
}
