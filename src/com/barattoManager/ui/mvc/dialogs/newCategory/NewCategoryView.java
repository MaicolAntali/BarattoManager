package com.barattoManager.ui.mvc.dialogs.newCategory;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;
import java.awt.*;

/**
 * View that shows the panel used to create a new category
 */
public class NewCategoryView implements View {

	private final JPanel mainPanel;

	@DocumentListenerField
	private final JTextField categoryNameField;
	@DocumentListenerField
	private final JTextField categoryDescriptionField;

	/**
	 * Constructor of the class
	 */
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

	/**
	 * Method used to get the name of category from a {@link JTextField}
	 *
	 * @return Name of the category as a {@link String}
	 */
	public String getCategoryName() {
		return categoryNameField.getText();
	}

	/**
	 * Method used to get the description of category from a {@link JTextField}
	 *
	 * @return Description of the category as a {@link String}
	 */
	public String getCategoryDescription() {
		return categoryDescriptionField.getText();
	}
}
