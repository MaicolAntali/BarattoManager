package com.barattoManager.ui.mvc.dialogs.newCategory;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;
import java.awt.*;

public class NewCategoryView implements BaseView {

	private final JPanel mainPanel;

	@DocumentListenerField
	private final JTextField categoryNameField;
	@DocumentListenerField
	private final JTextField categoryDescriptionField;

	public NewCategoryView() {
		mainPanel = new JPanel();
		categoryNameField = new JTextField(13);
		categoryDescriptionField = new JTextField(10);

		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel("Creazione di una nuova categoria:"));

		var categoryNamePanel = new JPanel();
		categoryNamePanel.add(new JLabel("Nome Categoria:"));
		categoryNamePanel.add(categoryNameField);

		var categoryDescriptionPanel = new JPanel();
		categoryDescriptionPanel.add(new JLabel("Descrizione Categoria:"));
		categoryDescriptionPanel.add(categoryDescriptionField);

		mainPanel.add(categoryNamePanel);
		mainPanel.add(categoryDescriptionPanel);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public String getCategoryName() {
		return categoryNameField.getText();
	}

	public String getCategoryDescription() {
		return categoryDescriptionField.getText();
	}
}
