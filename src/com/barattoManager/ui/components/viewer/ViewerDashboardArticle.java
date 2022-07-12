package com.barattoManager.ui.components.viewer;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.manager.factory.ArticleManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.menu.factory.DashboardMenuFactory;
import com.barattoManager.ui.customComponents.tree.article.ArticleTreeDashboard;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ViewerDashboardArticle extends JPanel implements DataChangeListener<String, Article> {
	public static final String HELP_MESSAGE = """
			In questa pagina puoi visualizzare i tuoi articoli
			Per effettuare un operazione su un tuo articolo puoi cliccare sul menu in alto al sinistra e scegliere di:
				 - Aggiungere un nuovo articolo da barattare;
				 - Cancellare l'offerta di un articolo.""";
	private final User user;

	private ArticleTreeDashboard articleTree;
	private JMenuBar menu;
	private JPanel mainPanel;
	private JButton backToHomeButton;
	private JPanel centerPanel;
	private JButton questionButton;

	/**
	 * {@link ViewerDashboardArticle} constructor
	 *
	 * @param dimension      Dimension of JPanel
	 * @param cardLayout     {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 * @param user           {@link User}
	 */
	public ViewerDashboardArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
		this.user = user;
		this.articleTree = new ArticleTreeDashboard(articleMapFilter(ArticleManagerFactory.getManager().getArticleMap()));

		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		menu = new DashboardMenuFactory().createMenuObject().createMenu(user, articleTree);
		centerPanel.add(menu, BorderLayout.NORTH);
		centerPanel.add(articleTree);


		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));

		questionButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
				HELP_MESSAGE,
				"Help",
				JOptionPane.INFORMATION_MESSAGE));
	}

	@Override
	public void update(ConcurrentHashMap<String, Article> updatedMap) {
		centerPanel.remove(articleTree);
		centerPanel.remove(menu);

		this.articleTree = new ArticleTreeDashboard(articleMapFilter(updatedMap));
		this.menu = new DashboardMenuFactory().createMenuObject().createMenu(user, articleTree);

		centerPanel.add(menu, BorderLayout.NORTH);
		centerPanel.add(articleTree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}

	private List<Article> articleMapFilter(ConcurrentHashMap<String, Article> articleMap) {
		return articleMap.values()
				.stream()
				.filter(article -> Objects.equals(article.getUserNameOwner().toLowerCase(), user.getUsername().toLowerCase()))
				.toList();
	}
}
