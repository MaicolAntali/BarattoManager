package com.barattoManager.ui.components;

import com.barattoManager.ui.customComponents.tree.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.event.RepaintListener;
import com.barattoManager.ui.customComponents.tree.meet.MeetEditorButtons;
import com.barattoManager.ui.customComponents.tree.meet.MeetTree;

import javax.swing.*;
import java.awt.*;

public class ConfiguratorMeetEditorUi extends JPanel implements RepaintListener {
	private final RepaintEventHandler repaintEventHandler = new RepaintEventHandler();
	private MeetTree meetTree = new MeetTree();
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JButton backToInitButton;

	/**
	 * {@link ConfiguratorCategoryEditorUi} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ConfiguratorMeetEditorUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		repaintEventHandler.addListener(this);

		centerPanel.add(meetTree);
		centerPanel.add(new MeetEditorButtons(repaintEventHandler), BorderLayout.SOUTH);


		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
	}

	@Override
	public void repaintCategoryTree() {
		centerPanel.remove(meetTree);

		meetTree = new MeetTree();

		centerPanel.add(meetTree);

		centerPanel.revalidate();
		centerPanel.revalidate();
	}
}
