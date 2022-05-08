package com.barattoManager.ui.panels.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel used to create a new field
 */
public class CreateNewFieldPanel extends JPanel {

	/**
	 * Field name field
	 */
	private final JTextField fieldName = new JTextField(13);
	/**
	 * Field required field
	 */
	private final JCheckBox fieldIsRequired = new JCheckBox();

	/**
	 * {@link CreateNewFieldPanel} constructor
	 */
	public CreateNewFieldPanel() {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel("Creazione di un nuovo campo."));

		var namePanel = new JPanel();
		namePanel.add(new JLabel("Nome campo:"));
		namePanel.add(fieldName);
		mainPanel.add(namePanel);

		var descriptionPanel = new JPanel();
		descriptionPanel.add(new JLabel("Campo obbligatorio:"));
		descriptionPanel.add(fieldIsRequired);
		mainPanel.add(descriptionPanel);

		setVisible(true);
		add(mainPanel);
	}

	/**
	 * Method used to get the {@link #fieldIsRequired}
	 * @return {@link JTextField} object
	 */
	public JTextField getFieldName() {
		return fieldName;
	}

	/**
	 * Method used to get the {@link #fieldIsRequired}
	 * @return {@link JCheckBox} object
	 */
	public JCheckBox getFieldIsRequired() {
		return fieldIsRequired;
	}
}
