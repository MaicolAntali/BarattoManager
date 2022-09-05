package com.barattoManager.ui.mvc.configurator.categoryEditor;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

public class CategoryEditorView implements View {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton backToHome;
	private JPanel treePanel;
	@ActionListenerField
	private JButton addRootCategoryButton;
	@ActionListenerField
	private JButton addSubCategoryButton;
	@ActionListenerField
	private JButton addFieldButton;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public void setTreePanel(JPanel tree) {
		this.treePanel.add(tree);
	}
}
