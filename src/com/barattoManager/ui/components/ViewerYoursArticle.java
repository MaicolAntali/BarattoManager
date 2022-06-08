package com.barattoManager.ui.components;

import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.event.RepaintListener;
import com.barattoManager.ui.customComponents.menu.yourArticle.ArticleTreeMenu;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent yours articles (only viewer)
 */
public class ViewerYoursArticle extends JPanel implements RepaintListener {

	/**
	 * User
	 */
	private final User user;
	/**
	 *Article Tree Menu
	 */
	private final ArticleTreeMenu articleTreeMenu;
	/**
	 * Article Tree
	 */
	private ArticleTree articleTree;
	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * Center JPanel {@code BorderLayout.CENTER}
	 */
	private JPanel centerPanel;
	/**
	 * Back button to {@link ViewerHomeUi}
	 */
	private JButton backToHomeButton;

	/**
	 * {@link ViewerYoursArticle} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 * @param user {@link User} owner of the article
	 */
	public ViewerYoursArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
		this.user = user;
		this.articleTree = new ArticleTree(user.getUsername(), null);

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

	/**
	 * Implementation of the method {@link RepaintListener#repaintComponents()}
	 * Is used to repaint the Article Tree
	 */
	@Override
	public void repaintComponents() {
		centerPanel.remove(articleTree);

		articleTree = new ArticleTree(user.getUsername(), null);
		articleTreeMenu.setArticleTree(articleTree);

		centerPanel.add(articleTree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}
}
