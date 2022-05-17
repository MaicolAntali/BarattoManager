package com.barattoManager.ui.customComponents.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel used to create a new field
 */
public class CreateNewFieldPanel extends JPanel {

	/**
	 * Creation of new field label
	 */
	private static final String LABEL_CREATION_OF_NEW_FIELD = "Creazione di un nuovo campo.";
	/**
	 * Field name label
	 */
	private static final String LABEL_FIELD_NAME = "Nome campo:";
	/**
	 * Obligatory field label
	 */
	private static final String LABEL_OBLIGATORY_FIELD = "Campo obbligatorio:";
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

		mainPanel.add(new JLabel(LABEL_CREATION_OF_NEW_FIELD));

		var namePanel = new JPanel();
		namePanel.add(new JLabel(LABEL_FIELD_NAME));
		namePanel.add(fieldName);
		mainPanel.add(namePanel);

		var descriptionPanel = new JPanel();
		descriptionPanel.add(new JLabel(LABEL_OBLIGATORY_FIELD));
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
