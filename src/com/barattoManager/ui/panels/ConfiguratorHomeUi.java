package com.barattoManager.ui.panels;

import javax.swing.*;
import java.awt.*;

public class ConfiguratorHomeUi extends JPanel {
	private JPanel mainPanel;
	private JButton modicaCategorieButton;

	public ConfiguratorHomeUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);

		modicaCategorieButton.addActionListener(e -> cardLayout.show(panelContainer, PanelName.CONF_CATEGORY_EDITOR.toString()));
	}
}
