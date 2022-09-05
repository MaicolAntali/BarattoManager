package com.barattoManager.ui.mvc.viewer.tradesViewer;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

public class ViewerTradesView implements View {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton backToHome;
	@ActionListenerField
	private JButton questionButton;
	private JPanel treePanel;
	private JPanel menuPanel;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public void setTreePanel(JPanel tree) {
		this.treePanel.add(tree);
	}

	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel.add(menuPanel);
	}
}
