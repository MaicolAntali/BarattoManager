package com.barattoManager.ui.components;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.event.RepaintListener;
import com.barattoManager.ui.customComponents.menu.factory.DashboardMenuFactory;
import com.barattoManager.ui.customComponents.tree.article.ArticleTreeDashboard;

import javax.swing.*;
import java.awt.*;

public class ViewerDashboardArticle extends JPanel implements RepaintListener {
	public static final String HELP_MESSAGE = """
			In questa pagina puoi visualizzare I tuoi articoli
			Per effettuare un operazione su un tuo articolo puoi cliccare sul menu in alto al sinistra e scegliere di:
				 - Aggiungere un nuovo articolo da barattare;
				 - Cancellare l'offerta di un articolo.""";
	private final User user;

	private ArticleTreeDashboard articleTree;
	private JPanel mainPanel;
	private JButton backToHomeButton;
	private JPanel centerPanel;
	private JButton questionButton;

	public ViewerDashboardArticle(Dimension dimension, CardLayout cardLayout, JPanel panelContainer, User user) {
		this.user = user;
		this.articleTree = new ArticleTreeDashboard(user.getUsername(), null);

		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		RepaintEventHandler repaintEventHandler = new RepaintEventHandler();
		repaintEventHandler.addListener(this);


		var articleTreeMenu = new DashboardMenuFactory().createMenuObject().createMenu();
		centerPanel.add(articleTreeMenu, BorderLayout.NORTH);
		centerPanel.add(articleTree);


		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));

		questionButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
				HELP_MESSAGE,
				"Help",
				JOptionPane.INFORMATION_MESSAGE));
	}

	/**
	 * Implementation of the method {@link RepaintListener#repaintComponents()}
	 * Is used to repaint the Article Tree
	 */
	@Override
	public void repaintComponents() {
		centerPanel.remove(articleTree);

		articleTree = new ArticleTreeDashboard(user.getUsername(), null);
		//articleTreeMenu.setArticleTree(articleTree);

		centerPanel.add(articleTree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}
}
