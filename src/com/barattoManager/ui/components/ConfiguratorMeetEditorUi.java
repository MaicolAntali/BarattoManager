package com.barattoManager.ui.components;

import com.barattoManager.ui.customComponents.tree.event.RepaintEventHandler;
import com.barattoManager.ui.customComponents.tree.event.RepaintListener;
import com.barattoManager.ui.customComponents.tree.meet.MeetEditorButtons;
import com.barattoManager.ui.customComponents.tree.meet.MeetTree;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the meet editor view (only configurator)
 */
public class ConfiguratorMeetEditorUi extends JPanel implements RepaintListener {
	/**
	 * {@link MeetTree} object
	 */
	private MeetTree meetTree = new MeetTree();
	/**
	 * Main Panel
	 */
	private JPanel mainPanel;
	/**
	 * Center Panel {@code BorderLayout.CENTER}
	 */
	private JPanel centerPanel;
	/**
	 * Back button to {@link InitialMenuUI}
	 */
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

		RepaintEventHandler repaintEventHandler = new RepaintEventHandler();
		repaintEventHandler.addListener(this);

		centerPanel.add(meetTree);
		centerPanel.add(new MeetEditorButtons(repaintEventHandler), BorderLayout.SOUTH);


		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
	}

	/**
	 * Implementation of the method {@link RepaintListener#repaintComponents()}
	 * Is used to repaint the Meet Tree
	 */
	@Override
	public void repaintComponents() {
		centerPanel.remove(meetTree);

		meetTree = new MeetTree();

		centerPanel.add(meetTree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}
}
