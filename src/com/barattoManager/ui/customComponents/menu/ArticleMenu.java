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
	/**
	 * Cancel offer command
	 */
	private static final String CANCEL_OFFER_COMMAND = "Cancella Offerta";
	/**
	 * Article Tree
	 */
	private ArticleTree articleTree;
	/**
	 * User
	 */
	private final User user;
	/**
	 * {@link RepaintEventHandler}
	 */
	private final RepaintEventHandler repaintEventHandler;

	public ArticleMenu(User user, RepaintEventHandler repaintEventHandler, ArticleTree articleTree) {
		this.articleTree = articleTree;
		this.user = user;
		this.repaintEventHandler = repaintEventHandler;

		add(generateMenu());
	}

	/**
	 * Inner class to manage menu actions
	 */
	class MenuAction extends AbstractAction {

		/**
		 * {@link HashMap} of actions
		 */
		private final static HashMap<String, MenuItemAction> ACTION_HASH_MAP;

		static {
			ACTION_HASH_MAP = new HashMap<>();

			ACTION_HASH_MAP.put("Nuovo", new NewArticle());
			ACTION_HASH_MAP.put(CANCEL_OFFER_COMMAND, new CancelOffer());
		}

		/**
		 * Father panel
		 */
		private final JPanel fatherPanel;

		/**
		 * {@link MenuAction} constructor
		 * @param name Action name
		 * @param fatherPanel JPanel
		 */
		public MenuAction(String name, JPanel fatherPanel) {
			super(name);
			this.fatherPanel = fatherPanel;
		}

		/**
		 * Method used to run the {@link MenuItemAction}
		 * @param e the event to be processed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			ACTION_HASH_MAP.get(getValue(Action.NAME).toString()).run(fatherPanel, repaintEventHandler, user, articleTree);
		}
	}

	/**
	 * Method used to set the {@link ArticleTree}
	 * @param articleTree {@link ArticleTree} object
	 */
	public void setArticleTree(ArticleTree articleTree) {
		this.articleTree = articleTree;
	}

	abstract JMenuBar generateMenu();
}
