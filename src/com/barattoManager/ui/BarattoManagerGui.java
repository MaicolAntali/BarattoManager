package com.barattoManager.ui;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.ui.components.*;

import javax.swing.*;
import java.awt.*;

/**
 * Class (that {@code extends JFrame}) used to instance every other view in a cardLayout and set the JFrame.
 */
public class BarattoManagerGui extends JFrame {
	/**
     * Baratto manager title
     */
    private static final String TITLE_BARATTO_MANAGER = "Baratto Manager";
    /**
     * Default dimensions of views
     */
    public static final Dimension CONTENT_PANEL_DEFAULT_DIMENSION = new Dimension(600, 430); // height - 70

    /**
     * Main JPanel
     */
    private JPanel mainPanel;
    /**
     * Panel with a cardLayout that contains each view
     */
    private JPanel panelContainer;
    /**
     * Label that show the app version
     */
    private JLabel versionLabel;

    /**
     * {@link BarattoManagerGui} constructor
     */
    public BarattoManagerGui() {
        // Frame config
        this.setTitle(TITLE_BARATTO_MANAGER);
        this.setSize(600, 500);
        this.setResizable(false);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

        // Set the app version
        versionLabel.setText(AppConfigurator.getInstance().getAppDataAsText("version"));

        // Set up the panelContainer
        CardLayout cardLayout = new CardLayout();
        panelContainer.setLayout(cardLayout);
        panelContainer.add(new InitialMenuUI(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.INITIAL_PANEL.toString());
        panelContainer.add(new LoginUI(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.LOGIN.toString());

        panelContainer.add(new ConfiguratorHomeUi(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.CONF_HOME.toString());
        panelContainer.add(new ConfiguratorCategoryEditorUi(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.CONF_CATEGORY_EDITOR.toString());
        panelContainer.add(new ConfiguratorMeetEditorUi(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.CONF_MEET_EDITOR.toString());

        panelContainer.add(new ViewerHomeUi(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_HOME.toString());
        panelContainer.add(new ViewerCategoryViewUi(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_CATEGORY.toString());
        panelContainer.add(new ViewerMeetViewUi(CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_MEET.toString());
    }
}