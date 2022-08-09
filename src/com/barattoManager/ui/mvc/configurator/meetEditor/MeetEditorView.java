package com.barattoManager.ui.mvc.configurator.meetEditor;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;

public class MeetEditorView implements BaseView {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton backToHome;
	private JPanel treePanel;
	@ActionListenerField
	private JButton addMeetButton;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public void setTreePanel(JPanel tree) {
		this.treePanel.add(tree);
	}
}
