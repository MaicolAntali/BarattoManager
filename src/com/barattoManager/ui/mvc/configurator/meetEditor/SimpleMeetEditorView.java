package com.barattoManager.ui.mvc.configurator.meetEditor;

import javax.swing.*;

/**
 * View the shows the meet editor (this view is only for the Configurator)
 */
public class SimpleMeetEditorView extends MeetEditorView {
	private JPanel mainPanel;
	private JPanel treePanel;
	private JButton backToHome;
	private JButton addMeetButton;

	/**
	 * Constructor of the class
	 */
	public SimpleMeetEditorView() {
		backToHome.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
		addMeetButton.addActionListener(e -> fireActionNotifierListener(e.getActionCommand().replace(" ", "_")));
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	@Override
	void setMeetTree(JPanel treePanel) {
		this.treePanel.add(treePanel);
	}
}
