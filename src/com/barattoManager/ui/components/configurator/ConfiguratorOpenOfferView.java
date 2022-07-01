package com.barattoManager.ui.components.configurator;

import com.barattoManager.ui.components.ComponentsName;

import javax.swing.*;
import java.awt.*;

public class ConfiguratorOpenOfferView extends JPanel{
    private JPanel mainPanel;
    private JButton backToInitButton;
    private JPanel centerPanel;

    public ConfiguratorOpenOfferView(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
        setVisible(true);
        add(mainPanel);
        mainPanel.setPreferredSize(dimension);

        backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
    }
}
