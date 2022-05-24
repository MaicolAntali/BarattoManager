package com.barattoManager.ui.components;

import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;
import java.awt.*;

public class ViewerYoursArticle extends JPanel {
	private JPanel mainPanel;
	private JButton backToHomeButton;
	private JPanel centerPanel;

	public ViewerYoursArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);


		centerPanel.add(new ArticleTree(user.getUsername()));


		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}
}
