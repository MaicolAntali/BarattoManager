package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.menu.actions.CancelOffer;
import com.barattoManager.ui.customComponents.menu.actions.MenuItemAction;
import com.barattoManager.ui.customComponents.menu.actions.NewArticle;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public abstract class ArticleMenu extends JPanel {

	private ArticleTree articleTree;

	private final User user;
	private final RepaintEventHandler repaintEventHandler;

	public ArticleMenu(User user, RepaintEventHandler repaintEventHandler, ArticleTree articleTree) {
		this.articleTree = articleTree;
		this.user = user;
		this.repaintEventHandler = repaintEventHandler;

		add(generateMenu());
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

	abstract JMenuBar generateMenu();
}
