package com.barattoManager.ui.components;

import com.barattoManager.article.Article;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;

import javax.swing.*;
import java.awt.*;

public class ViewerStoreArticle extends JPanel {

	private JPanel mainPanel;
	private JButton backToHomeButton;
	private JPanel centerPanel;

	public ViewerStoreArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);


		centerPanel.add(new ArticleTree(new Dimension(510, 310), "*", Article.State.OPEN_OFFERT));


		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}
}
