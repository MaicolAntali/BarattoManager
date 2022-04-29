package com.barattoManager.ui.components;

import javax.swing.*;
import java.awt.*;

public class InitialMenuUI extends JPanel {

    private JPanel mainPanel;
    private JPanel centerPanel;
    private JPanel topPanel;
    private JButton loginButton;

    public InitialMenuUI(Dimension dimension, CardLayout cardLayout, JPanel contentPanel) {
        // JPanel conf
        setVisible(true);
        add(mainPanel);

        mainPanel.setPreferredSize(dimension);

        loginButton.addActionListener(e -> {cardLayout.show(contentPanel, ComponentsName.LOGIN.toString());});
    }
}
