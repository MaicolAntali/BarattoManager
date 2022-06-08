package com.barattoManager.ui.components;

import com.barattoManager.article.Article;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;

import javax.swing.*;
import java.awt.*;
/**
 * Class used to create a JPanel that represent the store of the articles (only viewer)
 */
public class ViewerStoreArticle extends JPanel {
	/**
	 * Main Panel
	 */
	private JPanel mainPanel;
	/**
	 * Back button to {@link ViewerHomeUi}
	 */
	private JButton backToHomeButton;
	/**
	 * Center Panel {@code BorderLayout.CENTER}
	 */
	private JPanel centerPanel;

	/**
	 * {@link ViewerStoreArticle} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ViewerStoreArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);


		centerPanel.add(new ArticleTree(new Dimension(510, 310), "*", Article.State.OPEN_OFFER));


		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}
}
