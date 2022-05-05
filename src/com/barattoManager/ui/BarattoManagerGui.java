package com.barattoManager.ui;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.ui.components.*;

import javax.swing.*;
import java.awt.*;

public class BarattoManagerGui extends JFrame {

    private static final Dimension CONTENT_PANEL_DEFAULT_DIMENSION = new Dimension(600, 430); // height - 70

    private JPanel mainPanel;
    private JPanel panelContainer;
    private JLabel versionLabel;

    public BarattoManagerGui() {
        // Frame config
        this.setTitle("Baratto Manager");
        this.setSize(600, 500);
        this.setResizable(false);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the app version
        versionLabel.setText(AppConfigurator.getInstance().getAppDataAsText("version"));

        // Set up the panelContainer
        CardLayout cardLayout = new CardLayout();
        panelContainer.setLayout(cardLayout);
        panelContainer.add(new InitialMenuUI(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.INITIAL_PANEL.toString());
        panelContainer.add(new LoginUI(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.LOGIN.toString());
        panelContainer.add(new ConfiguratorHomeUi(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.CONF_HOME.toString());
        panelContainer.add(new ConfiguratorCategoryEditorUi(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.CONF_CATEGORY_EDITOR.toString());
    }
}
