package com.barattoManager.ui.components;

import com.barattoManager.ui.panels.optionPane.RegisterNewUserPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the configurator home view
 */
public class ConfiguratorHomeUi extends JPanel {
	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * JButton used to go in the {@link ConfiguratorCategoryEditorUi} view
	 */
	private JButton configCategoryButton;
	/**
	 * JButton used to add a new configurator
	 */
	private JButton addNewConfigurator;
	private JButton ConfiguraIncontri;

	/**
	 * {@link ConfiguratorHomeUi} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ConfiguratorHomeUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);

		configCategoryButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_CATEGORY_EDITOR.toString()));
		addNewConfigurator.addActionListener(e ->  new RegisterNewUserPanel(mainPanel, true).createNewUser());
	}
}
