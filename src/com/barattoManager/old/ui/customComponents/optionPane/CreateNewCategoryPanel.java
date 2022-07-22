package com.barattoManager.old.ui.customComponents.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} used to create a new category
 */
public class CreateNewCategoryPanel extends JPanel {

    private static final String LABEL_CREATION_OF_NEW_CATEGORY = "Creazione di una nuova categoria.";
    private static final String LABEL_CATEGORY_NAME = "Nome categoria:";
    private static final String LABEL_CATEGORY_DESCRIPTION = "Descrizione Categoria:";
    private final JTextField categoryName = new JTextField(13);
    private final JTextField categoryDescription = new JTextField(10);

    /**
     * Constructor of the class
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
     * Method used to get the {@link JTextField} with the category name
     *
     * @return {@link JTextField} with the category name
     */
    public JTextField getCategoryName() {
        return categoryName;
    }

    /**
     * Method used to get the {@link JTextField} with the category description
     *
     * @return {@link JTextField} with the category description
     */
    public JTextField getCategoryDescription() {
        return categoryDescription;
    }
}
