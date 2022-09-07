package com.barattoManager.ui.action.actions;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.exception.JsonException;
import com.barattoManager.exception.NullObjectException;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.action.Action;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonLoaderAction implements Action {

	private static final String LOAD_A_FILE_JSON = "Devi caricare un file JSON";
	private static final String ERROR = "Errore";
	private static final String ERROR_JSON_CONTAINS_FORMATTING_ERROR = "Il JSON contiene un errore nella formattazione\n\nErrore: %s";
	private static final String ERROR_IN_JSON_KEY = "Errore nelle chiavi del JSON";
	private static final String ERROR_CATEGORY_ALREADY_EXISTS = "La categoria %s esiste già";
	private static final String ERROR_INVALID_PATH_OF_CATEGORY = "Il percorso specificato per la categoria %s non è valido.";
	private static final String ERROR_INVALID_PATH_OF_THE_FIELD = "Il percorso specificato per il campo %s non è valido.";
	private static final String ERROR_FIELD_ALREADY_EXISTS = "Il campo %s esiste già";
	private static final String CATEGORY_NAME = "nome_categoria";
	private static final String FIELD_NAME = "nome_campo";
	private static final String DESCRIPTION = "descrizione";
	private static final String PATH = "percorso";
	private static final String OBLIGATORY = "obbligatorio";
	private final JPanel parentPanel;

	public JsonLoaderAction(JPanel parentPanel) {
		this.parentPanel = parentPanel;
	}


	public void run() {
		var jFileChooser = new JFileChooser();
		jFileChooser.setFileFilter(new FileNameExtensionFilter("*.json", "JSON"));

		var option = jFileChooser.showOpenDialog(parentPanel);
		if (option == JFileChooser.APPROVE_OPTION) {

			var selectedFile = jFileChooser.getSelectedFile();

			if (!getFileExtension(selectedFile).equalsIgnoreCase("json")) {
				new MessageDialogDisplay()
						.setParentComponent(parentPanel)
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle(ERROR)
						.setMessage(LOAD_A_FILE_JSON)
						.show();
				return;
			}

			ObjectNode[] nodes;
			try {
				nodes = getObjectNodes(selectedFile);
			} catch (InvalidArgumentException e) {
				new MessageDialogDisplay()
						.setParentComponent(parentPanel)
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle(ERROR)
						.setMessage(e.getMessage())
						.show();
				return;
			}

			var errors = new ArrayList<String>();

			for (ObjectNode node : nodes) {
				if (node.fields().next().getKey().contains("_categoria")) {
					try {
						createCategory(errors, node);
					} catch (JsonException e) {
						new MessageDialogDisplay()
								.setParentComponent(parentPanel)
								.setMessageType(JOptionPane.ERROR_MESSAGE)
								.setTitle(ERROR)
								.setMessage(e.getMessage())
								.show();
						return;
					}
				}
				else {
					try {
						createField(errors, node);
					} catch (JsonException e) {
						new MessageDialogDisplay()
								.setParentComponent(parentPanel)
								.setMessageType(JOptionPane.ERROR_MESSAGE)
								.setTitle(ERROR)
								.setMessage(e.getMessage())
								.show();
						return;
					}
				}
			}

			if (!errors.isEmpty()) {
				new MessageDialogDisplay()
						.setParentComponent(parentPanel)
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle(ERROR)
						.setMessage(String.join("\n", errors))
						.show();
			}

		}
	}

	private String getFileExtension(File selectedFile) {
		int index = selectedFile.getName().lastIndexOf('.');

		if (index > 0) {
			return selectedFile.getName().substring(index + 1);
		}

		return "";
	}

	private ObjectNode[] getObjectNodes(File selectedFile) throws InvalidArgumentException {
		var objectMapper = new ObjectMapper();

		ObjectNode[] node;
		try {
			node = objectMapper.readValue(selectedFile, ObjectNode[].class);
		} catch (JsonMappingException ex) {
			throw new InvalidArgumentException(ERROR_JSON_CONTAINS_FORMATTING_ERROR.formatted(ex.getMessage()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		return node;
	}


	private HashMap<String, String> getJsonNodeFields(JsonNode jsonNode) {

		var nodeMap = new HashMap<String, String>();

		for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields(); it.hasNext(); ) {
			var fields = it.next();

			nodeMap.put(fields.getKey(), fields.getValue().asText());
		}

		return nodeMap;
	}

	private ArrayList<String> getPercorso(HashMap<String, String> jsonMap) throws JsonException {
		var array = new ArrayList<String>();

		if (jsonMap.get(PATH) == null) {
			throw new JsonException(ERROR_IN_JSON_KEY);
		}

		array.add("root");
		Collections.addAll(array, jsonMap.get(PATH).split("\\|"));

		return array;
	}

	private void createCategory(ArrayList<String> errors, ObjectNode jsonNode) throws JsonException {
		var jsonMap = getJsonNodeFields(jsonNode);

		if (Objects.equals(jsonMap.get(PATH), "null")) {
			if (jsonMap.get(CATEGORY_NAME) == null || jsonMap.get(DESCRIPTION) == null)
				throw new JsonException(ERROR_IN_JSON_KEY);

			try {
				CategoryManagerFactory.getManager()
						.addNewMainCategory(String.valueOf(jsonMap.get(CATEGORY_NAME)), jsonMap.get(DESCRIPTION));
			} catch (AlreadyExistException ex) {
				errors.add(ERROR_CATEGORY_ALREADY_EXISTS.formatted(jsonMap.get(CATEGORY_NAME)));
			} catch (InvalidArgumentException | NullObjectException ex) {
				throw new RuntimeException(ex);
			}
		}
		else {
			if (jsonMap.get(CATEGORY_NAME) == null || jsonMap.get(DESCRIPTION) == null)
				throw new JsonException(ERROR_IN_JSON_KEY);

			try {
				CategoryManagerFactory.getManager()
						.addNewSubCategory(getPercorso(jsonMap), jsonMap.get(CATEGORY_NAME), jsonMap.get(DESCRIPTION));
			} catch (AlreadyExistException ex) {
				errors.add(ERROR_CATEGORY_ALREADY_EXISTS.formatted(jsonMap.get(CATEGORY_NAME)));
			} catch (NullObjectException ex) {
				errors.add(ERROR_INVALID_PATH_OF_CATEGORY.formatted(jsonMap.get(CATEGORY_NAME)));
			} catch (InvalidArgumentException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	private void createField(ArrayList<String> errors, ObjectNode jsonNode) throws JsonException {
		var jsonMap = getJsonNodeFields(jsonNode);

		if (jsonMap.get(FIELD_NAME) == null || jsonMap.get(OBLIGATORY) == null)
			throw new JsonException(ERROR_IN_JSON_KEY);

		try {
			CategoryManagerFactory.getManager()
					.addNewField(getPercorso(jsonMap), jsonMap.get(FIELD_NAME), Objects.equals(jsonMap.get(OBLIGATORY), "true"));
		} catch (AlreadyExistException ex) {
			errors.add(ERROR_FIELD_ALREADY_EXISTS.formatted(jsonMap.get(FIELD_NAME)));
		} catch (NullObjectException ex) {
			errors.add(ERROR_INVALID_PATH_OF_THE_FIELD.formatted(jsonMap.get(FIELD_NAME)));
		} catch (InvalidArgumentException ex) {
			throw new RuntimeException(ex);
		}
	}
}
