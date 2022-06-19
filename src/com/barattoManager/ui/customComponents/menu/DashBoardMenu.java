package com.barattoManager.ui.customComponents.menu;

import javax.swing.*;
import java.awt.*;

public class DashBoardMenu extends JPanel implements Menu {
	@Override
	public JMenuBar createMenu() {
		var articleMenu = new JMenu("Articoli");

		var articleMenuItemNew = articleMenu.add(new JMenuItem("Nuovo"));
		articleMenuItemNew.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
		articleMenuItemNew.addActionListener(e -> System.out.println("NUOVO"));

		articleMenu.add(new JMenuItem("Cancella Offerta"));

		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		return menuBar;
	}
}
