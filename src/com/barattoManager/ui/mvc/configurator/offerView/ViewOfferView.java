package com.barattoManager.ui.mvc.configurator.offerView;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

/**
 * View that shows the offers (this view is only for the Configurator)
 */
public class ViewOfferView implements View {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton backToHome;
	private JPanel treePanel;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	/**
	 * Method used to set the treePanel
	 * @param tree {@link JPanel}
	 */
	public void setTreePanel(JPanel tree) {
		this.treePanel.add(tree);
	}
}
