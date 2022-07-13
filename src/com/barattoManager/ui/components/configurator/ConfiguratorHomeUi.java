package com.barattoManager.ui.components.configurator;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.NullCategoryException;
import com.barattoManager.manager.factory.CategoryManagerFactory;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.optionPane.RegisterNewUserPanel;
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
 * Class used to create a JPanel that represent the configurator home view
 */
public class ConfiguratorHomeUi extends JPanel {
	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * JButton used to go in the {@link ConfiguratorCategoryEditorUi} view
	 */
	private JButton configCategoryButton;
	/**
	 * JButton used to add a new configurator
	 */
	private JButton addNewConfigurator;
	/**
	 * JButton used to go in the {@link ConfiguratorMeetEditorUi} view
	 */
	private JButton configMeetButton;
	/**
	 * JButton used to go in the {@link ConfiguratorOfferView} view
	 */
	private JButton showOffer;
	private JButton loadJsonButton;

	/**
	 * {@link ConfiguratorHomeUi} constructor
	 *
	 * @param dimension      Dimension of JPanel
	 * @param cardLayout     {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ConfiguratorHomeUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);

		configCategoryButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_CATEGORY_EDITOR.toString()));
		configMeetButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_MEET_EDITOR.toString()));
		addNewConfigurator.addActionListener(e -> new RegisterNewUserPanel(mainPanel, true).createNewUser());
		showOffer.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_OFFER_VIEW.toString()));

		loadJsonButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("*.json", "JSON"));
			int result = fileChooser.showOpenDialog(this);

			if (result == JFileChooser.APPROVE_OPTION) {
				var  errors       = new ArrayList<String>();
				File selectedFile = fileChooser.getSelectedFile();


				if (!getFileExtension(selectedFile).equalsIgnoreCase("json")) {
					JOptionPane.showMessageDialog(this, "Devi caricare un file JSON", "Errori", JOptionPane.ERROR_MESSAGE);
					return;
				}

				getFileExtension(selectedFile);


				ObjectNode[] node = getObjectNodes(selectedFile);

				Arrays.stream(node).sequential()
						.forEach(jsonNode -> {
							if (jsonNode.fields().next().getKey().contains("_categoria")) {
								createCategory(errors, jsonNode);
							}
							else {
								createField(errors, jsonNode);
							}
						});

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

	private ObjectNode[] getObjectNodes(File selectedFile) {
		var objectMapper = new ObjectMapper();

		ObjectNode[] node;
		try {
			node = objectMapper.readValue(selectedFile, ObjectNode[].class);
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

	private ArrayList<String> getPercorso(HashMap<String, String> jsonMap) {
		var array = new ArrayList<String>();

		array.add("root");
		Collections.addAll(array, jsonMap.get("percorso").split("\\|"));

		return array;
	}

	private void createCategory(ArrayList<String> errors, ObjectNode jsonNode) {
		var jsonMap = getJsonNodeFields(jsonNode);

		if (Objects.equals(jsonMap.get("percorso"), "null")) {
			try {
				CategoryManagerFactory.getManager()
						.addNewMainCategory(String.valueOf(jsonMap.get("nome_categoria")), jsonMap.get("descrizione"));
			} catch (AlreadyExistException ex) {
				errors.add("La categoria %s esiste già".formatted(jsonMap.get("nome_categoria")));
			} catch (IllegalValuesException | NullCategoryException ex) {
				throw new RuntimeException(ex);
			}
		}
		else {
			try {
				CategoryManagerFactory.getManager()
						.addNewSubCategory(getPercorso(jsonMap), jsonMap.get("nome_categoria"), jsonMap.get("descrizione"));
			} catch (AlreadyExistException ex) {
				errors.add("La categoria %s esiste già".formatted(jsonMap.get("nome_categoria")));
			} catch (NullCategoryException ex) {
				errors.add("Il percorso specificato per la categoria %s non è valido.".formatted(jsonMap.get("nome_categoria")));
			} catch (IllegalValuesException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	private void createField(ArrayList<String> errors, ObjectNode jsonNode) {
		var jsonMap = getJsonNodeFields(jsonNode);
		try {
			CategoryManagerFactory.getManager()
					.addNewField(getPercorso(jsonMap), jsonMap.get("nome_campo"), Objects.equals(jsonMap.get("obbligatorio"), "true"));
		} catch (AlreadyExistException ex) {
			errors.add("Il campo %s esiste già".formatted(jsonMap.get("nome_campo")));
		} catch (NullCategoryException ex) {
			errors.add("Il percorso specificato per il campo %s non è valido.".formatted(jsonMap.get("nome_campo")));
		} catch (IllegalValuesException ex) {
			throw new RuntimeException(ex);
		}
	}
}
