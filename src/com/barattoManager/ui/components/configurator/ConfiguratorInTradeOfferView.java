package com.barattoManager.ui.components.configurator;

import com.barattoManager.ui.components.ComponentsName;

import javax.swing.*;
import java.awt.*;

public class ConfiguratorInTradeOfferView extends JPanel{
    private JPanel mainPanel;
    private JButton backToHomeButton;
    private JPanel centerPanel;

    public ConfiguratorInTradeOfferView(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
        setVisible(true);
        add(mainPanel);
        mainPanel.setPreferredSize(dimension);

        backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
    }
}
