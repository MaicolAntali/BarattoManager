package com.barattoManager.ui.mvc.menu.yourArticle;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;
import java.awt.*;

public class YourArticleMenuView implements BaseView {

	private final JPanel mainPanel;
	@ActionListenerField
	private final JMenuItem newArticleAction;
	@ActionListenerField
	private final JMenuItem cancelArticleAction;

	public YourArticleMenuView() {
		mainPanel = new JPanel();
		var articleMenu = new JMenu("Articoli");

		newArticleAction = articleMenu.add(new JMenuItem("Nuovo"));
		newArticleAction.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

		cancelArticleAction = articleMenu.add(new JMenuItem("Cancella Offerta"));
		cancelArticleAction.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		menuBar.setPreferredSize(new Dimension(500, 30));

		mainPanel.add(menuBar);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public JMenuItem getNewArticleAction() {
		return newArticleAction;
	}

	public JMenuItem getCancelArticleAction() {
		return cancelArticleAction;
	}
}
