package com.barattoManager.ui.mvc.dialogs.newField;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;
import java.awt.*;

public class NewFieldView implements BaseView {

	private final JPanel mainPanel;

	@DocumentListenerField
	private final JTextField fieldName;
	@ActionListenerField
	private final JCheckBox fieldRequired;

	public NewFieldView() {
		mainPanel = new JPanel();
		fieldName = new JTextField(13);
		fieldRequired = new JCheckBox();

		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel("Creazione di un nuovo campo:"));

		var categoryNamePanel = new JPanel();
		categoryNamePanel.add(new JLabel("Nome Campo:"));
		categoryNamePanel.add(fieldName);

		var categoryDescriptionPanel = new JPanel();
		categoryDescriptionPanel.add(new JLabel("Campo Obbligatorio:"));
		categoryDescriptionPanel.add(fieldRequired);

		mainPanel.add(categoryNamePanel);
		mainPanel.add(categoryDescriptionPanel);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public String getFieldName() {
		return fieldName.getText();
	}

	public boolean getFieldRequired() {
		return fieldRequired.isSelected();
	}
}
