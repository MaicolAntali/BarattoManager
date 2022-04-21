package com.barattoManager.ui.panels;

import com.barattoManager.ui.components.CategoryViewEditor;

import javax.swing.*;
import java.awt.*;

public class ConfiguratorCategoryEditorUi extends JPanel {
	private JPanel mainPanel;
	private JPanel centerPanel;

	public ConfiguratorCategoryEditorUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		centerPanel.add(new CategoryViewEditor());
	}
}
