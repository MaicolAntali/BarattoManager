package com.barattoManager.ui.components;

import javax.swing.*;
import java.awt.*;

public class NewFieldInput extends JPanel {

	private final JTextField fieldName = new JTextField(13);
	private final JCheckBox fieldIsRequired = new JCheckBox();

	public NewFieldInput() {
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

	public JTextField getFieldName() {
		return fieldName;
	}

	public JCheckBox getFieldIsRequired() {
		return fieldIsRequired;
	}
}
