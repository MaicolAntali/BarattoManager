package com.barattoManager.ui.panels.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a Jpanel used to cerate a new category
 */
public class CreateNewCategoryPanel extends JPanel {

	/**
	 * Category nam,e field
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

		mainPanel.add(new JLabel("Creazione di una nuova categoria."));

		var namePanel = new JPanel();
		namePanel.add(new JLabel("Nome categoria:"));
		namePanel.add(categoryName);
		mainPanel.add(namePanel);

		var descriptionPanel = new JPanel();
		descriptionPanel.add(new JLabel("Descrizione Categoria:"));
		descriptionPanel.add(categoryDescription);
		mainPanel.add(descriptionPanel);

		setVisible(true);
		add(mainPanel);
	}

	/**
	 * Method used to get the {@link #categoryName}
	 * @return {@link JTextField} object
	 */
	public JTextField getCategoryName() {
		return categoryName;
	}

	/**
	 * Method used to get the {@link #categoryDescription}
	 * @return {@link JTextField} object
	 */
	public JTextField getCategoryDescription() {
		return categoryDescription;
	}
}
