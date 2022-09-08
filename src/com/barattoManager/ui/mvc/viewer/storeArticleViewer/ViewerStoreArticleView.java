package com.barattoManager.ui.mvc.viewer.storeArticleViewer;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

/**
 * View that shows the store of articles (this view is only for the Viewer)
 */
public class ViewerStoreArticleView implements View {
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

	/**
	 * Method used to set the treePanel
	 * @param tree {@link JPanel}
	 */
	public void setTreePanel(JPanel tree) {
		this.treePanel.add(tree);
	}

	/**
	 * Method used to set the menuPanel
	 * @param menuPanel {@link JPanel}
	 */
	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel.add(menuPanel);
	}
}
