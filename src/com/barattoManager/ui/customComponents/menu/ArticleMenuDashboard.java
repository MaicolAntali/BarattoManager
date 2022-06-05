package com.barattoManager.ui.customComponents.menu;

import com.barattoManager.ui.customComponents.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.article.ArticleTree;
import com.barattoManager.user.User;

import javax.swing.*;
import java.awt.*;

public class ArticleMenuDashboard extends ArticleMenu {
	public ArticleMenuDashboard(User user, RepaintEventHandler repaintEventHandler, ArticleTree articleTree) {
		super(user, repaintEventHandler, articleTree);
	}

	@Override
	JMenuBar generateMenu() {
		var articleMenu = new JMenu("Articoli");

		var articleMenuItemNew = articleMenu.add(new MenuAction("Nuovo", this));
		articleMenuItemNew.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

		articleMenu.add(new MenuAction("Cancella Offerta", this));


		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		menuBar.setPreferredSize(new Dimension(500, 27));
		return menuBar;
	}
}
