package com.barattoManager.ui.panels;

import com.barattoManager.ui.components.CategoryViewEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfiguratorCategoryEditorUi extends JPanel {
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JButton backToInitButton;

	public ConfiguratorCategoryEditorUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		centerPanel.add(new CategoryViewEditor());

		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, PanelName.CONF_HOME.toString()));
	}
}
