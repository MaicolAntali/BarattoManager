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
 * Class used to create a {@link JPanel} that allows the viewer to exchange the {@link Article articles}
 */
public class ViewerStoreArticle extends JPanel implements DataChangeListener<String, Article> {

	private static final String HELP_MESSAGE = "Nello store dei articoli puoi visualizzare gli articoli che gli altri utenti hanno messo in baratto " +
			"\n Per scambiare un tuo articolo con uno di questi puoi cliccare sul menu in alto a sinistra e scegliere l'opzione di scambio.";
	private final User user;
	private JMenuBar menu;
	private JPanel mainPanel;
	private JButton backToHomeButton;
	private JPanel centerPanel;
	private JButton questionButton;
	private Tree tree;

	/**
	 * Constructor of the class
	 *
	 * @param dimension      {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout     {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
	 * @param user           {@link User} who has logged in
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
