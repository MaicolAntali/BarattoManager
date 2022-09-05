package com.barattoManager.ui.mvc.menu.trades;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;
import java.awt.*;

public class TradeMenuView implements View {

	private final JPanel mainPanel;
	@ActionListenerField
	private final JMenuItem acceptTradeAction;
	@ActionListenerField
	private final JMenuItem acceptRescheduleAction;

	public TradeMenuView() {
		mainPanel = new JPanel();
		var articleMenu = new JMenu("Scambi");

		acceptTradeAction = articleMenu.add(new JMenuItem("Accetta"));
		acceptTradeAction.setAccelerator(KeyStroke.getKeyStroke('T', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

		acceptRescheduleAction = articleMenu.add(new JMenuItem("Accetta ma riprogramma"));
		acceptRescheduleAction.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

		var menuBar = new JMenuBar();
		menuBar.add(articleMenu);
		menuBar.setPreferredSize(new Dimension(500, 30));

		mainPanel.add(menuBar);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public JMenuItem getAcceptTradeAction() {
		return acceptTradeAction;
	}

	public JMenuItem getAcceptRescheduleAction() {
		return acceptRescheduleAction;
	}
}
