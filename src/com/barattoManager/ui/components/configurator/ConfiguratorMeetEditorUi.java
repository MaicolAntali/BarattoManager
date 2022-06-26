package com.barattoManager.ui.components.configurator;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.components.InitialMenuUI;
import com.barattoManager.ui.customComponents.buttons.MeetEditorButtons;
import com.barattoManager.ui.customComponents.tree.meet.MeetTree;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the meet editor view (only configurator)
 */
public class ConfiguratorMeetEditorUi extends JPanel implements DataChangeListener {
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

		centerPanel.add(meetTree);
		centerPanel.add(new MeetEditorButtons(), BorderLayout.SOUTH);

		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString()));
	}

	@Override
	public void update() {
		centerPanel.remove(meetTree);

		meetTree = new MeetTree();

		centerPanel.add(meetTree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}
}
