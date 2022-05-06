package com.barattoManager.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a Jpanel that represent the initial view
 */
public class InitialMenuUI extends JPanel {

    /**
     * Main Jpanel
     */
    private JPanel mainPanel;
    /**
     * Jbutton used to log in
     */
    private JButton loginButton;

    /**
     * {@link InitialMenuUI} constructor
     * @param dimension Dimension of Jpanel
     * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
     * @param contentPanel {@link JPanel} object that contains every cards
     */
    public InitialMenuUI(Dimension dimension, CardLayout cardLayout, JPanel contentPanel) {
        // JPanel conf
        setVisible(true);
        add(mainPanel);

        mainPanel.setPreferredSize(dimension);

        loginButton.addActionListener(e -> {cardLayout.show(contentPanel, ComponentsName.LOGIN.toString());});
    }
}
