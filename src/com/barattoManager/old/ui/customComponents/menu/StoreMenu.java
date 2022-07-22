package com.barattoManager.old.ui.customComponents.menu;

import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.ui.customComponents.menu.actions.MenuAction;
import com.barattoManager.old.ui.customComponents.menu.actions.TradeArticle;
import com.barattoManager.old.ui.customComponents.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

/**
 * Class used to create a menu that manages the store
 */
public class StoreMenu extends JPanel implements Menu {
	private final static HashMap<String, MenuAction> COMMAND_MAP = new HashMap<>() {{
		put("Scambia Articolo", new TradeArticle());
	}};


	@Override
	public JMenuBar createMenu(User user, Tree tree) {
		var articleMenu = new JMenu("Scambi");

		var newTradeAction = articleMenu.add(new JMenuItem("Scambia Articolo"));
		newTradeAction.setAccelerator(KeyStroke.getKeyStroke('T', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		newTradeAction.addActionListener(e -> runCommand(e, user, tree));

		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		return menuBar;
	}

	/**
	 * Method used to run actions
	 *
	 * @param event {@link ActionEvent}
	 * @param user  {@link User} who has logged in
	 * @param tree  {@link Tree} on which the actions of the menu will perform
	 */
	public void runCommand(ActionEvent event, User user, Tree tree) {
		COMMAND_MAP.get(event.getActionCommand()).run(user, tree);
	}
}
