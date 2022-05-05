package com.barattoManager.ui.components;

import com.barattoManager.ui.panels.categoryTree.RepaintingListeners;
import com.barattoManager.ui.panels.categoryTree.TreeContainer;

import javax.swing.*;
import java.awt.*;

public class ConfiguratorCategoryEditorUi extends JPanel {
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JButton backToInitButton;

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
