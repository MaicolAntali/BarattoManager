package com.barattoManager.ui.components.viewer;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.menu.factory.StoreMenuFactory;
import com.barattoManager.ui.customComponents.tree.Tree;
import com.barattoManager.ui.customComponents.tree.article.ArticleTreeStore;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to create a JPanel that represent the store of the articles (only viewer)
 */
public class ViewerStoreArticle extends JPanel implements DataChangeListener<String, Article> {

	/**
	 * Help message: explains what are you seeing and how to exchange articles
	 */
	private static final String HELP_MESSAGE = "Nello store dei articoli puoi visuallizzare gli articoli che gli altri utenti hanno messo in baratto " +
			"\n Per scambiare un tuo articolo con uno di questi puoi cliccare sul menu in alto a sinistra e scegliere l'opzione di scambio.";

	private final User user;
	private JMenuBar menu;

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
	private JButton questionButton;

	private Tree tree;

	/**
	 * {@link ViewerStoreArticle} constructor
	 *
	 * @param dimension      Dimension of JPanel
	 * @param cardLayout     {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 * @param user           {@link User}
	 */
	public ViewerStoreArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
		this.user = user;

		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		tree = new ArticleTreeStore(articleMapFilter(ArticleManagerFactory.getManager().getArticleMap()));

		menu = new StoreMenuFactory().createMenuObject().createMenu(user, tree);
		centerPanel.add(menu, BorderLayout.NORTH);

		centerPanel.add(tree);

		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));

		questionButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
				HELP_MESSAGE, "Help",
				JOptionPane.INFORMATION_MESSAGE));
	}

	@Override
	public void update(ConcurrentHashMap<String, Article> updatedMap) {
		centerPanel.remove(tree);
		centerPanel.remove(menu);

		this.tree = new ArticleTreeStore(articleMapFilter(updatedMap));
		this.menu = new StoreMenuFactory().createMenuObject().createMenu(user, tree);

		centerPanel.add(menu, BorderLayout.NORTH);
		centerPanel.add(tree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}

	private List<Article> articleMapFilter(ConcurrentHashMap<String, Article> articleMap) {
		return articleMap.values()
				.stream()
				.filter(article -> !Objects.equals(article.getUserNameOwner().toLowerCase(), user.getUsername().toLowerCase()))
				.filter(article -> article.getArticleState() == Article.State.OPEN_OFFER)
				.toList();
	}
}
