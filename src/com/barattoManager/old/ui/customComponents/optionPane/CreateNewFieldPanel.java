package com.barattoManager.old.ui.customComponents.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} used to create a new field
 */
public class CreateNewFieldPanel extends JPanel {

	private static final String LABEL_CREATION_OF_NEW_FIELD = "Creazione di un nuovo campo.";
	private static final String LABEL_FIELD_NAME = "Nome campo:";
	private static final String LABEL_OBLIGATORY_FIELD = "Campo obbligatorio:";
	private final JTextField fieldName = new JTextField(13);
	private final JCheckBox fieldIsRequired = new JCheckBox();

	/**
	 * Constructor of the class
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
	 * Method used to get the {@link JTextField} with the field name
	 *
	 * @return {@link JTextField} with the field name
	 */
	public JTextField getFieldName() {
		return fieldName;
	}

	/**
	 * Method used to get the {{@link JCheckBox} with field is required
	 *
	 * @return {@link JCheckBox} with field is required
	 */
	public JCheckBox getFieldIsRequired() {
		return fieldIsRequired;
	}
}
