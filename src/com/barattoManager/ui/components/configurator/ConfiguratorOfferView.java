package com.barattoManager.ui.components.configurator;

import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.tree.article.ArticleTreeStore;

import javax.swing.*;
import java.awt.*;

public class ConfiguratorOfferView extends JPanel{
    private JPanel mainPanel;
    private JButton backToHomeButton;
    private JPanel centerPanel;

    public ConfiguratorOfferView(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
        setVisible(true);
        add(mainPanel);
        mainPanel.setPreferredSize(dimension);

        centerPanel.add(new ArticleTreeStore(new Dimension(520, 330), "", null));


        backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
    }
}
