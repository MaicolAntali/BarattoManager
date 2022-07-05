package com.barattoManager.ui.components.viewer;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.menu.factory.StoreMenuFactory;
import com.barattoManager.ui.customComponents.tree.Tree;
import com.barattoManager.ui.customComponents.tree.article.ArticleTreeStore;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the store of the articles (only viewer)
 */
public class ViewerStoreArticle extends JPanel implements DataChangeListener {

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

	public ViewerStoreArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
		this.user = user;

		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		tree = new ArticleTreeStore(new Dimension(510, 310), "!%s".formatted(user.getUsername()), Article.State.OPEN_OFFER);

		menu = new StoreMenuFactory().createMenuObject().createMenu(user, tree);
		centerPanel.add(menu, BorderLayout.NORTH);

		centerPanel.add(tree);

		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));

		questionButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
				"Nello store dei articoli puoi visuallizzare gli articoli che gli altri utenti hanno messo in baratto " +
						"\n Per scambiare un tuo articolo con uno di questi puoi cliccare sul menu in alto a sinistra e scegliere l'opzione di scambio.",
				"Help",
				JOptionPane.INFORMATION_MESSAGE));
	}

	@Override
	public void update() {
		centerPanel.remove(tree);
		centerPanel.remove(menu);

		this.menu = new StoreMenuFactory().createMenuObject().createMenu(user, tree);
		this.tree = new ArticleTreeStore(new Dimension(510, 310), "!%s".formatted(user.getUsername()), Article.State.OPEN_OFFER);

		centerPanel.add(menu, BorderLayout.NORTH);
		centerPanel.add(tree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}
}
