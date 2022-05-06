package com.barattoManager.ui.components;

import com.barattoManager.ui.panels.categoryTree.RepaintingListeners;
import com.barattoManager.ui.panels.categoryTree.TreeContainer;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a Jpanel that represent the category editor view (only configurator)
 */
public class ConfiguratorCategoryEditorUi extends JPanel {
	/**
	 * Main Jpanel
	 */
	private JPanel mainPanel;
	/**
	 * Center Jpanel {@code BorderLayout.CENTER}
	 */
	private JPanel centerPanel;
	/**
	 * Back button to {@link InitialMenuUI}
	 */
	private JButton backToInitButton;

	/**
	 * {@link ConfiguratorCategoryEditorUi} constructor
	 * @param dimension Dimension of Jpanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ConfiguratorCategoryEditorUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		var treeContainer = new TreeContainer();
		centerPanel.add(treeContainer);
		RepaintingListeners.getInstance().addListener(treeContainer);

		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
	}

}
