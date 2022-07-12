package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.MenuAction;
import com.barattoManager.ui.customComponents.menu.actions.TradeArticle;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

/**
 * Store Menu
 */
public class StoreMenu extends JPanel implements Menu {

	/**
	 * {@link HashMap} of commands
	 */
	private final static HashMap<String, MenuAction> COMMAND_MAP = new HashMap<>(){{
		put("Scambia Articolo", new TradeArticle());
	}};


	/**
	 * Method used to create a {@link JMenuBar}
	 * @param user {@link User}
	 * @param tree {@link Tree}
	 * @return {@link JMenuBar}
	 */
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
	 * Method used to run the commands
	 * @param event {@link ActionEvent}
	 * @param user {@link User}
	 * @param tree {@link Tree}
	 */
	public void runCommand(ActionEvent event, User user, Tree tree) {
		COMMAND_MAP.get(event.getActionCommand()).run(user, tree);
	}
}
