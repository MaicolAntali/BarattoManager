package com.barattoManager.ui.components;

import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.event.RepaintListener;
import com.barattoManager.ui.customComponents.menu.yourArticle.ArticleTreeMenu;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;
import java.awt.*;

public class ViewerYoursArticle extends JPanel implements RepaintListener {

	private final User user;
	private final ArticleTreeMenu articleTreeMenu;

	private ArticleTree articleTree;
	private JPanel mainPanel;
	private JButton backToHomeButton;
	private JPanel centerPanel;

	public ViewerYoursArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
		this.user = user;
		this.articleTree = new ArticleTree(user.getUsername());

		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		RepaintEventHandler repaintEventHandler = new RepaintEventHandler();
		repaintEventHandler.addListener(this);


		this.articleTreeMenu = new ArticleTreeMenu(user, repaintEventHandler, articleTree);
		centerPanel.add(articleTreeMenu , BorderLayout.NORTH);
		centerPanel.add(articleTree);


		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}

	@Override
	public void repaintCategoryTree() {
		centerPanel.remove(articleTree);

		articleTree = new ArticleTree(user.getUsername());
		articleTreeMenu.setArticleTree(articleTree);

		centerPanel.add(articleTree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}
}
