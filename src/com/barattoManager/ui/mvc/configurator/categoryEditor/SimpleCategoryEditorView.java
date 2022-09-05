package com.barattoManager.ui.mvc.configurator.categoryEditor;

import javax.swing.*;

public class SimpleCategoryEditorView extends CategoryEditorView {
	private JPanel mainPanel;
	private JPanel treeContainer;
	private JButton backToHome;
	private JButton addRootCategoryButton;
	private JButton addSubCategoryButton;
	private JButton addFieldButton;

	public SimpleCategoryEditorView() {
		backToHome.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
		addRootCategoryButton.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
		addSubCategoryButton.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
		addFieldButton.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	@Override
	void setCategoryTree(JPanel treePanel) {
		this.treeContainer.add(treePanel);
	}
}
