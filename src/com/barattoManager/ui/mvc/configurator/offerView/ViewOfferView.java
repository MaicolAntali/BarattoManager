package com.barattoManager.ui.mvc.configurator.offerView;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;

public class ViewOfferView implements BaseView {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton backToHome;
	private JPanel treePanel;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public void setTreePanel(JPanel tree) {
		this.treePanel.add(tree);
	}
}