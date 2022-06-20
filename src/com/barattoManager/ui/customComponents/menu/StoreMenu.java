package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.menu.actions.MenuAction;
import com.barattoManager.ui.customComponents.menu.actions.TradeArticle;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class StoreMenu extends JPanel implements Menu {

	private final static HashMap<String, MenuAction> COMMAND_MAP = new HashMap<>(){{
		put("Scambia Articolo", new TradeArticle());
	}};


	@Override
	public JMenuBar createMenu(RepaintEventHandler repaintEventHandler, User user, Tree tree) {
		var articleMenu = new JMenu("Scambi");

		var newTradeAction = articleMenu.add(new JMenuItem("Scambia Articolo"));
		newTradeAction.setAccelerator(KeyStroke.getKeyStroke('T', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		newTradeAction.addActionListener(e -> runCommand(e, repaintEventHandler, user, tree));

		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		return menuBar;
	}

	public void runCommand(ActionEvent event, RepaintEventHandler repaintEventHandler, User user, Tree tree) {
		COMMAND_MAP.get(event.getActionCommand()).run(repaintEventHandler, user, tree);
	}
}
