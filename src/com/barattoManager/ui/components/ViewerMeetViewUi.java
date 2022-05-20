package com.barattoManager.ui.components;

import com.barattoManager.ui.customComponents.tree.meet.MeetTree;

import javax.swing.*;
import java.awt.*;

public class ViewerMeetViewUi extends JPanel {
	private JPanel mainPanel;
	private JButton backToHomeButton;
	private JPanel centerPanel;

	public ViewerMeetViewUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		centerPanel.add(new MeetTree(new Dimension(520, 330)));

		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}
}
