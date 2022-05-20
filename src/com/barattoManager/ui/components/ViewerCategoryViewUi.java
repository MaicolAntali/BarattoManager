package com.barattoManager.ui.components;

import com.barattoManager.ui.customComponents.tree.category.CategoryTree;

import javax.swing.*;
import java.awt.*;

public class ViewerCategoryViewUi extends JPanel {
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JButton backToHomeButton;

	public ViewerCategoryViewUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		centerPanel.add(new CategoryTree(new Dimension(520, 330)));

		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}
}
