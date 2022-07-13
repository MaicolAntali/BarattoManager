package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.menu.actions.AcceptRescheduleTrade;
import com.barattoManager.ui.customComponents.menu.actions.AcceptTrade;
import com.barattoManager.ui.customComponents.menu.actions.MenuAction;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

/**
 * Class used to create a menu that manages the trade
 */
public class TradeMenu extends JPanel implements Menu {

	private final static HashMap<String, MenuAction> COMMAND_MAP = new HashMap<>() {{
		put("Accetta", new AcceptTrade());
		put("Accetta ma riprogramma", new AcceptRescheduleTrade());
	}};


	@Override
	public JMenuBar createMenu(User user, Tree tree) {
		var articleMenu = new JMenu("Scambi");

		var acceptTradeAction = articleMenu.add(new JMenuItem("Accetta"));
		acceptTradeAction.setAccelerator(KeyStroke.getKeyStroke('T', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		acceptTradeAction.addActionListener(e -> runCommand(e, user, tree));

		var acceptRescheduleAction = articleMenu.add(new JMenuItem("Accetta ma riprogramma"));
		acceptRescheduleAction.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		acceptRescheduleAction.addActionListener(e -> runCommand(e, user, tree));

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
