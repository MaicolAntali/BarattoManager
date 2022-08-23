package com.barattoManager.ui.mvc.configurator.homepage;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.exception.JsonException;
import com.barattoManager.exception.NullObjectException;
import com.barattoManager.services.category.CategoryManagerFactory;
import com.barattoManager.ui.mvc.base.BaseView;
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

public class JsonLoader {

	public void loadJson(BaseView view) {
		var jFileChooser = new JFileChooser();
		jFileChooser.setFileFilter(new FileNameExtensionFilter("*.json", "JSON"));

		var option = jFileChooser.showOpenDialog(view.getMainJPanel());
		if (option == JFileChooser.APPROVE_OPTION) {

			var selectedFile = jFileChooser.getSelectedFile();

			if (!getFileExtension(selectedFile).equalsIgnoreCase("json")) {
				new MessageDialogDisplay()
						.setParentComponent(view.getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
						.setMessage("Devi caricare un file JSON")
						.show();
				return;
			}

			ObjectNode[] nodes;
			try {
				nodes = getObjectNodes(selectedFile);
			} catch (InvalidArgumentException e) {
				new MessageDialogDisplay()
						.setParentComponent(view.getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
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
								.setParentComponent(view.getMainJPanel())
								.setMessageType(JOptionPane.ERROR_MESSAGE)
								.setTitle("Errore")
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
								.setParentComponent(view.getMainJPanel())
								.setMessageType(JOptionPane.ERROR_MESSAGE)
								.setTitle("Errore")
								.setMessage(e.getMessage())
								.show();
						return;
					}
				}
			}

			if (!errors.isEmpty()) {
				new MessageDialogDisplay()
						.setParentComponent(view.getMainJPanel())
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.setTitle("Errore")
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
			throw new InvalidArgumentException("Il JSON contiene un errore nella formattazione\n\nErrore: %s".formatted(ex.getMessage()));
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

		if (jsonMap.get("percorso") == null) {
			throw new JsonException("Errore nelle chiavi del JSON");
		}

		array.add("root");
		Collections.addAll(array, jsonMap.get("percorso").split("\\|"));

		return array;
	}

	private void createCategory(ArrayList<String> errors, ObjectNode jsonNode) throws JsonException {
		var jsonMap = getJsonNodeFields(jsonNode);

		if (Objects.equals(jsonMap.get("percorso"), "null")) {
			if (jsonMap.get("nome_categoria") == null || jsonMap.get("descrizione") == null)
				throw new JsonException("Errore nelle chiavi del JSON");

			try {
				CategoryManagerFactory.getManager()
						.addNewMainCategory(String.valueOf(jsonMap.get("nome_categoria")), jsonMap.get("descrizione"));
			} catch (AlreadyExistException ex) {
				errors.add("La categoria %s esiste già".formatted(jsonMap.get("nome_categoria")));
			} catch (InvalidArgumentException | NullObjectException ex) {
				throw new RuntimeException(ex);
			}
		}
		else {
			if (jsonMap.get("nome_categoria") == null || jsonMap.get("descrizione") == null)
				throw new JsonException("Errore nelle chiavi del JSON");

			try {
				CategoryManagerFactory.getManager()
						.addNewSubCategory(getPercorso(jsonMap), jsonMap.get("nome_categoria"), jsonMap.get("descrizione"));
			} catch (AlreadyExistException ex) {
				errors.add("La categoria %s esiste già".formatted(jsonMap.get("nome_categoria")));
			} catch (NullObjectException ex) {
				errors.add("Il percorso specificato per la categoria %s non è valido.".formatted(jsonMap.get("nome_categoria")));
			} catch (InvalidArgumentException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	private void createField(ArrayList<String> errors, ObjectNode jsonNode) throws JsonException {
		var jsonMap = getJsonNodeFields(jsonNode);

		if (jsonMap.get("nome_campo") == null || jsonMap.get("obbligatorio") == null)
			throw new JsonException("Errore nelle chiavi del JSON");

		try {
			CategoryManagerFactory.getManager()
					.addNewField(getPercorso(jsonMap), jsonMap.get("nome_campo"), Objects.equals(jsonMap.get("obbligatorio"), "true"));
		} catch (AlreadyExistException ex) {
			errors.add("Il campo %s esiste già".formatted(jsonMap.get("nome_campo")));
		} catch (NullObjectException ex) {
			errors.add("Il percorso specificato per il campo %s non è valido.".formatted(jsonMap.get("nome_campo")));
		} catch (InvalidArgumentException ex) {
			throw new RuntimeException(ex);
		}
	}

}
