package com.barattoManager.ui.components;

import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.optionPane.RegisterNewUserPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} that shows the initial menu to the {@link com.barattoManager.model.user.User users}
 */
public class InitialMenuUI extends JPanel {

    private JPanel mainPanel;
    private JButton loginButton;
    private JButton RegisterButton;

    /**
     * Constructor of the class
     *
     * @param dimension    {@link Dimension} of the {@link JPanel} to be created
     * @param cardLayout   {@link CardLayout} object that represent the type layout
     * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
     */
    public InitialMenuUI(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
        // JPanel conf
        setVisible(true);
        add(mainPanel);

        mainPanel.setPreferredSize(dimension);


        loginButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.LOGIN.toString()));
        RegisterButton.addActionListener(e -> new RegisterNewUserPanel(mainPanel, false).createNewUser());
    }
}
