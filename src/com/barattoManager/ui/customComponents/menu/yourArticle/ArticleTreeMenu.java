package com.barattoManager.ui.customComponents.menu.yourArticle;

import com.barattoManager.ui.customComponents.menu.yourArticle.actions.MenuItemAction;
import com.barattoManager.ui.customComponents.menu.yourArticle.actions.NewArticle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class ArticleTreeMenu extends JPanel {

	class MenuAction extends AbstractAction {

		private final static HashMap<String, MenuItemAction> ACTION_HASH_MAP;

		static {
			 ACTION_HASH_MAP = new HashMap<>();

			 ACTION_HASH_MAP.put("Nuovo", new NewArticle());
		}

		private final JPanel fatherPanel;

		public MenuAction(String name, JPanel fatherPanel) {
			super(name);

			this.fatherPanel = fatherPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ACTION_HASH_MAP.get(getValue(Action.NAME).toString()).run(fatherPanel);
		}
	}

	public ArticleTreeMenu() {

		var articleMenu = new JMenu("Articoli");
		articleMenu.add(new MenuAction("Nuovo", this));

		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		menuBar.setPreferredSize(new Dimension(500, 27));
		add(menuBar);
	}
}
