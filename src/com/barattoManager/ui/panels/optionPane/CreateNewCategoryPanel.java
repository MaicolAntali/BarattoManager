package com.barattoManager.ui.panels.optionPane;

import javax.swing.*;
import java.awt.*;

public class CreateNewCategoryPanel extends JPanel {

	private final JTextField categoryName = new JTextField(13);
	private final JTextField categoryDescription = new JTextField(10);

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

	public JTextField getCategoryName() {
		return categoryName;
	}

	public JTextField getCategoryDescription() {
		return categoryDescription;
	}
}
