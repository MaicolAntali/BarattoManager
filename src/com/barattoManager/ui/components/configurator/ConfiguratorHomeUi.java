package com.barattoManager.ui.components.configurator;

import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.optionPane.RegisterNewUserPanel;

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
	/**
	 * JButton used to go in the {@link ConfiguratorMeetEditorUi} view
	 */
	private JButton configMeetButton;
	/**
	 * JButton used to go in the {@link ConfiguratorOfferView} view
	 */
	private JButton showOffer;

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
		configMeetButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_MEET_EDITOR.toString()));
		addNewConfigurator.addActionListener(e ->  new RegisterNewUserPanel(mainPanel, true).createNewUser());
		showOffer.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_OFFER_VIEW.toString()));
	}
}
