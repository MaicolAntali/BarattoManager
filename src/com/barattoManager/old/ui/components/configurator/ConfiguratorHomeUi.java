package com.barattoManager.old.ui.components.configurator;

import com.barattoManager.old.exception.AlreadyExistException;
import com.barattoManager.old.exception.IllegalValuesException;
import com.barattoManager.old.exception.JsonException;
import com.barattoManager.old.exception.NullCategoryException;
import com.barattoManager.old.manager.factory.CategoryManagerFactory;
import com.barattoManager.old.ui.components.ComponentsName;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class used to create a {@link JPanel} that shows the configurator's home page
 */
public class ConfiguratorHomeUi extends JPanel {

	private static final String HELP_MESSAGE = """
			Formato del JSON:
			[
				{
					"nome_categoria": "nome_categoria",
			        "descrizione": "descrizione_categoria",
			        "percorso": null
			    },
			    {
					"nome_categoria": "nome_categoria",
					"descrizione": "descrizione_categoria",
					"percorso": "main|sub|sub"
				},
				{
					"nome_campo": "nome_campo",
					"obbligatorio": true,
					"percorso": "main|sub|sub"
					}
			]
			NB: - in una categoria radice il percorso deve essere null
			    - in una sotto categoria e in un campo"
			           - il percorso non deve essere null
			           - il percorso deve essere descritto con il carattere: |
			""";
	private static final String UPLOAD_JSON_FILE = "Devi caricare un file JSON";
	private static final String ERROR_IN_READING_JSON = "Errore nella lettura del JSON";
	private static final String ERROR_JSON_CONTAINS_FORMATTING_ERROR = "Il JSON contiene un errore nella formattazione\n\nErrore: %s";
	private static final String ERROR_IN_JSON_KEYS = "Errore nelle chiavi del JSON";
	private static final String ERROR_CATEGORY_ALREADY_EXIST = "La categoria %s esiste già";
	private static final String ERROR_PATH_CATEGORY_NOT_VALID = "Il percorso specificato per la categoria %s non è valido.";
	private static final String ERROR_FIELD_ALREADY_EXIST = "Il campo %s esiste già";
	private static final String PATH_FIELD_NOT_VALID = "Il percorso specificato per il campo %s non è valido.";

	private JPanel mainPanel;
	private JButton configCategoryButton;
	private JButton addNewConfigurator;
	private JButton configMeetButton;
	private JButton showOffer;
	private JButton loadJsonButton;
	private JButton questionButton;

	/**
	 * Constructor of the class
	 *
	 * @param dimension      {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout     {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
	 */
	public ConfiguratorHomeUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);

		configCategoryButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_CATEGORY_EDITOR.toString()));
		configMeetButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_MEET_EDITOR.toString()));

		/* FIXME: applicare mvc al panello
		addNewConfigurator.addActionListener(e -> new RegisterNewUserPanel(mainPanel, true).createNewUser());
		 */

		showOffer.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_OFFER_VIEW.toString()));
		questionButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
				HELP_MESSAGE,
				"Help",
				JOptionPane.INFORMATION_MESSAGE));

		loadJsonButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("*.json", "JSON"));
			int result = fileChooser.showOpenDialog(this);

			if (result == JFileChooser.APPROVE_OPTION) {
				var  errors       = new ArrayList<String>();
				File selectedFile = fileChooser.getSelectedFile();

				if (!getFileExtension(selectedFile).equalsIgnoreCase("json")) {
					JOptionPane.showMessageDialog(this, UPLOAD_JSON_FILE, "Errori", JOptionPane.ERROR_MESSAGE);
					return;
				}

				ObjectNode[] node;
				try {
					node = getObjectNodes(selectedFile);
				} catch (IllegalValuesException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_IN_READING_JSON, JOptionPane.ERROR_MESSAGE);
					return;
				}

				for (ObjectNode jsonNode: node) {
					if (jsonNode.fields().next().getKey().contains("_categoria")) {
						try {
							createCategory(errors, jsonNode);
						} catch (JsonException ex) {
							JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_IN_READING_JSON, JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					else {
						try {
							createField(errors, jsonNode);
						} catch (JsonException ex) {
							JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_IN_READING_JSON, JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}



				if (!errors.isEmpty()) {
					JOptionPane.showMessageDialog(this, String.join("\n", errors), "Errori", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private String getFileExtension(File selectedFile) {
		int index = selectedFile.getName().lastIndexOf('.');

		if (index > 0) {
			return selectedFile.getName().substring(index + 1);
		}

		return "";
	}

	private ObjectNode[] getObjectNodes(File selectedFile) throws IllegalValuesException {
		var objectMapper = new ObjectMapper();

		ObjectNode[] node;
		try {
			node = objectMapper.readValue(selectedFile, ObjectNode[].class);
		} catch (JsonMappingException ex) {
			throw new IllegalValuesException(ERROR_JSON_CONTAINS_FORMATTING_ERROR.formatted(ex.getMessage()));
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
			throw new JsonException(ERROR_IN_JSON_KEYS);
		}

		array.add("root");
		Collections.addAll(array, jsonMap.get("percorso").split("\\|"));

		return array;
	}

	private void createCategory(ArrayList<String> errors, ObjectNode jsonNode) throws JsonException {
		var jsonMap = getJsonNodeFields(jsonNode);

		if (Objects.equals(jsonMap.get("percorso"), "null")) {
			if (jsonMap.get("nome_categoria") == null || jsonMap.get("descrizione") == null)
				throw new JsonException(ERROR_IN_JSON_KEYS);

			try {
				CategoryManagerFactory.getManager()
						.addNewMainCategory(String.valueOf(jsonMap.get("nome_categoria")), jsonMap.get("descrizione"));
			} catch (AlreadyExistException ex) {
				errors.add(ERROR_CATEGORY_ALREADY_EXIST.formatted(jsonMap.get("nome_categoria")));
			} catch (IllegalValuesException | NullCategoryException ex) {
				throw new RuntimeException(ex);
			}
		}
		else {
			if (jsonMap.get("nome_categoria") == null || jsonMap.get("descrizione") == null)
				throw new JsonException(ERROR_IN_JSON_KEYS);

			try {
				CategoryManagerFactory.getManager()
						.addNewSubCategory(getPercorso(jsonMap), jsonMap.get("nome_categoria"), jsonMap.get("descrizione"));
			} catch (AlreadyExistException ex) {
				errors.add(ERROR_CATEGORY_ALREADY_EXIST.formatted(jsonMap.get("nome_categoria")));
			} catch (NullCategoryException ex) {
				errors.add(ERROR_PATH_CATEGORY_NOT_VALID.formatted(jsonMap.get("nome_categoria")));
			} catch (IllegalValuesException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	private void createField(ArrayList<String> errors, ObjectNode jsonNode) throws JsonException {
		var jsonMap = getJsonNodeFields(jsonNode);

		if (jsonMap.get("nome_campo") == null || jsonMap.get("obbligatorio") == null)
			throw new JsonException(ERROR_IN_JSON_KEYS);

		try {
			CategoryManagerFactory.getManager()
					.addNewField(getPercorso(jsonMap), jsonMap.get("nome_campo"), Objects.equals(jsonMap.get("obbligatorio"), "true"));
		} catch (AlreadyExistException ex) {
			errors.add(ERROR_FIELD_ALREADY_EXIST.formatted(jsonMap.get("nome_campo")));
		} catch (NullCategoryException ex) {
			errors.add(PATH_FIELD_NOT_VALID.formatted(jsonMap.get("nome_campo")));
		} catch (IllegalValuesException ex) {
			throw new RuntimeException(ex);
		}
	}
}
