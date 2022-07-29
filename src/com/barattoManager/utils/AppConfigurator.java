package com.barattoManager.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;

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

	public static AppConfigurator getInstance() {
		if (instance == null)
			instance = new AppConfigurator();

		return instance;
	}

	public String getAppDataAsText(String nodeName) {
		return node.get("app").get(nodeName).asText();
	}

	public String getFileName(String nodeName) {
		return node.get("file").get(nodeName).asText();
	}

	public String getPasswordSetting(String nodeName) {
		return node.get("password").get(nodeName).asText();
	}

	public JsonNode getDefaultField() {
		return node.get("default_category");
	}

}