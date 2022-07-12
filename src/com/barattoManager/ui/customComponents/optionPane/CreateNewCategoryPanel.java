package com.barattoManager.ui.customComponents.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel used to create a new category
 */
public class CreateNewCategoryPanel extends JPanel {

	/**
	 * Creation of new category label
	 */
	private static final String LABEL_CREATION_OF_NEW_CATEGORY = "Creazione di una nuova categoria.";
	/**
	 * Category name label
	 */
	private static final String LABEL_CATEGORY_NAME = "Nome categoria:";
	/**
	 * Category description label
	 */
	private static final String LABEL_CATEGORY_DESCRIPTION = "Descrizione Categoria:";
	/**
	 * Category name field
	 */
	private final JTextField categoryName = new JTextField(13);
	/**
	 * Category description field
	 */
	private final JTextField categoryDescription = new JTextField(10);

	/**
	 * {@link CreateNewCategoryPanel} constructor
	 */
	public CreateNewCategoryPanel() {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel(LABEL_CREATION_OF_NEW_CATEGORY));

		var namePanel = new JPanel();
		namePanel.add(new JLabel(LABEL_CATEGORY_NAME));
		namePanel.add(categoryName);
		mainPanel.add(namePanel);

		var descriptionPanel = new JPanel();
		descriptionPanel.add(new JLabel(LABEL_CATEGORY_DESCRIPTION));
		descriptionPanel.add(categoryDescription);
		mainPanel.add(descriptionPanel);

		setVisible(true);
		add(mainPanel);
	}

	/**
	 * Method used to get the {@link #categoryName}
	 *
	 * @return {@link JTextField} object
	 */
	public JTextField getCategoryName() {
		return categoryName;
	}

	/**
	 * Method used to get the {@link #categoryDescription}
	 *
	 * @return {@link JTextField} object
	 */
	public JTextField getCategoryDescription() {
		return categoryDescription;
	}
}
