package com.barattoManager.ui.components.configurator;

import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.components.InitialMenuUI;
import com.barattoManager.ui.customComponents.tree.article.ArticleTreeStore;

import javax.swing.*;
import java.awt.*;

public class ConfiguratorOfferView extends JPanel{
    /**
     * Main Panel
     */
    private JPanel mainPanel;
    /**
     * Center Panel {@code BorderLayout.CENTER}
     */
    private JPanel centerPanel;
    /**
     * Back button to {@link ConfiguratorHomeUi}
     */
    private JButton backToHomeButton;

    /**
     * {@link ConfiguratorOfferView} constructor
     * @param dimension Dimension of JPanel
     * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
     * @param panelContainer {@link JPanel} object that contains every cards
     */
    public ConfiguratorOfferView(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
        setVisible(true);
        add(mainPanel);
        mainPanel.setPreferredSize(dimension);

        centerPanel.add(new ArticleTreeStore(new Dimension(520, 330), "", null));

        backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
    }
}
