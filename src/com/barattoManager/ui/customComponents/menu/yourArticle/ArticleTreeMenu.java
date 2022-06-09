package com.barattoManager.ui.customComponents.menu.yourArticle;

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

/**
 * Class used to create a JPanel that contains menu for yours article view
 */
public class ArticleTreeMenu extends JPanel {
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

	/**
	 * {@link ArticleTreeMenu} constructor
	 * @param user {@link User} object
	 * @param repaintEventHandler {@link RepaintEventHandler} object
	 * @param articleTree {@link ArticleTree} object
	 */
	public ArticleTreeMenu(User user, RepaintEventHandler repaintEventHandler, ArticleTree articleTree) {
		this.articleTree = articleTree;
		this.user = user;
		this.repaintEventHandler = repaintEventHandler;

		var articleMenu = new JMenu("Articoli");

		var articleMenuItemNew = articleMenu.add(new MenuAction("Nuovo", this));
		articleMenuItemNew.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

		articleMenu.add(new MenuAction(CANCEL_OFFER_COMMAND, this));


		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		menuBar.setPreferredSize(new Dimension(500, 27));
		add(menuBar);
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
}
