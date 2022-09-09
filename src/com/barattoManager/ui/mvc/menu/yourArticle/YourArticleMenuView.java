package com.barattoManager.ui.mvc.menu.yourArticle;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;
import java.awt.*;

/**
 * View used to show your article menu
 */
public class YourArticleMenuView implements View {

	private final JPanel mainPanel;
	@ActionListenerField
	private final JMenuItem newArticleAction;
	@ActionListenerField
	private final JMenuItem cancelArticleAction;

	/**
	 * Constructor of the class
	 */
	public YourArticleMenuView() {
		mainPanel = new JPanel();
		var articleMenu = new JMenu("Articoli");

		newArticleAction = articleMenu.add(new JMenuItem("Nuovo"));
		newArticleAction.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

		cancelArticleAction = articleMenu.add(new JMenuItem("Cancella Offerta"));

		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		menuBar.setPreferredSize(new Dimension(500, 30));

		mainPanel.add(menuBar);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

}
