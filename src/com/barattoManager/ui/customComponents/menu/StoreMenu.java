package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;

public class StoreMenu extends JPanel implements Menu {


	@Override
	public JMenuBar createMenu(RepaintEventHandler repaintEventHandler, User user, Tree tree) {
		var articleMenu = new JMenu("Scambi");

		var newTradeAction = articleMenu.add(new JMenuItem("Scambi Articolo"));

		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		return menuBar;
	}
}
