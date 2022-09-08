package com.barattoManager.ui.mvc.viewer.categoryViewer;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

/**
* View that shows the category (this view is only for the Viewer)
*/
public class ViewerCategoryView implements View {
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
