package com.barattoManager.ui.components;

import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.tree.article.ArticleTreeStore;

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

	public ViewerStoreArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);


		centerPanel.add(new ArticleTreeStore(new Dimension(510, 310), "!%s".formatted(user.getUsername()), Article.State.OPEN_OFFER));


		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}
}
