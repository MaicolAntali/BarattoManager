package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.model.user.User;
import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.menu.actions.CancelOffer;
import com.barattoManager.ui.customComponents.menu.actions.MenuAction;
import com.barattoManager.ui.customComponents.menu.actions.NewArticle;
import com.barattoManager.ui.customComponents.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class DashBoardMenu extends JPanel implements Menu {

	private final static HashMap<String, MenuAction> COMMAND_MAP = new HashMap<>(){{
		put("Nuovo", new NewArticle());
		put("Cancella Offerta", new CancelOffer());
	}};


	@Override
	public JMenuBar createMenu(RepaintEventHandler repaintEventHandler, User user, Tree tree) {
		var articleMenu = new JMenu("Articoli");

		var newArticleAction = articleMenu.add(new JMenuItem("Nuovo"));
		newArticleAction.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		newArticleAction.addActionListener(e -> runCommand(e, repaintEventHandler, user, tree));

		var cancelArticleAction = articleMenu.add(new JMenuItem("Cancella Offerta"));
		cancelArticleAction.addActionListener(e -> runCommand(e, repaintEventHandler, user, tree));

		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		return menuBar;
	}

	public void runCommand(ActionEvent event, RepaintEventHandler repaintEventHandler, User user, Tree tree) {
		COMMAND_MAP.get(event.getActionCommand()).run(repaintEventHandler, user, tree);
	}
}
