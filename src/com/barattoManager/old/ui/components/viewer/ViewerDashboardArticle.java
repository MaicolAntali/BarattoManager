package com.barattoManager.old.ui.components.viewer;

import com.barattoManager.event.DataChangeListener;
import com.barattoManager.old.manager.factory.ArticleManagerFactory;
import com.barattoManager.old.sample.article.Article;
import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.ui.components.ComponentsName;
import com.barattoManager.old.ui.customComponents.menu.factory.DashboardMenuFactory;
import com.barattoManager.old.ui.customComponents.tree.article.ArticleTreeDashboard;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to create a {@link JPanel} that allows the viewer to manage his {@link Article articles}
 */
public class ViewerDashboardArticle extends JPanel implements DataChangeListener<String, Article> {
	private static final String HELP_MESSAGE = """
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
	 * Constructor of the class
	 *
	 * @param dimension      {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout     {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
	 * @param user           {@link User} who has logged in
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
