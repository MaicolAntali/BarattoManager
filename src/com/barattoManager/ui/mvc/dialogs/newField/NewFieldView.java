package com.barattoManager.ui.mvc.dialogs.newField;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;
import java.awt.*;

/**
 * View that shows the panel used to create a new field
 */
public class NewFieldView implements View {

	private static final String LABEL_CREATION_OF_NEW_FIELD = "Creazione di un nuovo campo:";
	private static final String LABEL_FILED_NAME = "Nome Campo:";
	private static final String LABEL_REQUIRED_FIELD = "Campo Obbligatorio:";
	private final JPanel mainPanel;

	@DocumentListenerField
	private final JTextField fieldName;
	@ActionListenerField
	private final JCheckBox fieldRequired;

	/**
	 * Constructor of the class
	 */
	public NewFieldView() {
		mainPanel = new JPanel();
		fieldName = new JTextField(13);
		fieldRequired = new JCheckBox();

		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel(LABEL_CREATION_OF_NEW_FIELD));

		var categoryNamePanel = new JPanel();
		categoryNamePanel.add(new JLabel(LABEL_FILED_NAME));
		categoryNamePanel.add(fieldName);

		var categoryDescriptionPanel = new JPanel();
		categoryDescriptionPanel.add(new JLabel(LABEL_REQUIRED_FIELD));
		categoryDescriptionPanel.add(fieldRequired);

		mainPanel.add(categoryNamePanel);
		mainPanel.add(categoryDescriptionPanel);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	/**
	 * Method used to get the field name from a {@link JTextField}
	 *
	 * @return Name of the field as a {@link String}
	 */
	public String getFieldName() {
		return fieldName.getText();
	}

	/**
	 * Method used to get the field requirement from a {@link JCheckBox}
	 *
	 * @return field requirement as a {@link Boolean}
	 */
	public boolean getFieldRequired() {
		return fieldRequired.isSelected();
	}
}
