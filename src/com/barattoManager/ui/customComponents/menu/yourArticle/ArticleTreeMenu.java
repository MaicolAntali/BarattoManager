package com.barattoManager.ui.customComponents.menu.yourArticle;

import com.barattoManager.article.Article;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.menu.yourArticle.actions.CancelOffer;
import com.barattoManager.ui.customComponents.menu.yourArticle.actions.MenuItemAction;
import com.barattoManager.ui.customComponents.menu.yourArticle.actions.NewArticle;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class ArticleTreeMenu extends JPanel {

	private ArticleTree articleTree;

	private final User user;
	private final RepaintEventHandler repaintEventHandler;

	public ArticleTreeMenu(User user, RepaintEventHandler repaintEventHandler, ArticleTree articleTree) {
		this.articleTree = articleTree;
		this.user = user;
		this.repaintEventHandler = repaintEventHandler;

		var articleMenu = new JMenu("Articoli");

		var articleMenuItemNew = articleMenu.add(new MenuAction("Nuovo", this));
		articleMenuItemNew.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

		articleMenu.add(new MenuAction("Cancella Offerta", this));


		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		menuBar.setPreferredSize(new Dimension(500, 27));
		add(menuBar);
	}

	class MenuAction extends AbstractAction {

		private final static HashMap<String, MenuItemAction> ACTION_HASH_MAP;

		static {
			ACTION_HASH_MAP = new HashMap<>();

			ACTION_HASH_MAP.put("Nuovo", new NewArticle());
			ACTION_HASH_MAP.put("Cancella Offerta", new CancelOffer());
		}

		private final JPanel fatherPanel;

		public MenuAction(String name, JPanel fatherPanel) {
			super(name);

			this.fatherPanel = fatherPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ACTION_HASH_MAP.get(getValue(Action.NAME).toString()).run(fatherPanel, repaintEventHandler, user, articleTree);
		}
	}

	public void setArticleTree(ArticleTree articleTree) {
		this.articleTree = articleTree;
	}
}
