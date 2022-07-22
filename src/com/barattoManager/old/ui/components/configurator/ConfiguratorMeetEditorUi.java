package com.barattoManager.old.ui.components.configurator;

import com.barattoManager.event.DataChangeListener;
import com.barattoManager.old.manager.factory.MeetManagerFactory;
import com.barattoManager.old.sample.meet.Meet;
import com.barattoManager.old.ui.components.ComponentsName;
import com.barattoManager.old.ui.customComponents.buttons.MeetEditorButtons;
import com.barattoManager.old.ui.customComponents.tree.meet.MeetTree;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to create a {@link JPanel} that allows the configurator to manage the {@link Meet meets}
 */
public class ConfiguratorMeetEditorUi extends JPanel implements DataChangeListener<String, Meet> {

	private MeetTree meetTree = new MeetTree(MeetManagerFactory.getManager().getMeets());
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JButton backToInitButton;

	/**
	 * Constructor of the class
	 *
	 * @param dimension      {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout     {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
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
	public void update(ConcurrentHashMap<String, Meet> updatedMap) {
		centerPanel.remove(meetTree);

		meetTree = new MeetTree(updatedMap.values().stream().toList());

		centerPanel.add(meetTree);

		centerPanel.repaint();
		centerPanel.revalidate();
	}
}
