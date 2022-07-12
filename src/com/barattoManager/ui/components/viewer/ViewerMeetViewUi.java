package com.barattoManager.ui.components.viewer;

import com.barattoManager.manager.factory.MeetManagerFactory;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.tree.meet.MeetTree;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the meets view (only viewer)
 */
public class ViewerMeetViewUi extends JPanel {
	/**
	 * Main Panel
	 */
	private JPanel mainPanel;
	/**
	 * Center Panel {@code BorderLayout.CENTER}
	 */
	private JPanel centerPanel;
	/**
	 * Back button to {@link ViewerHomeUi}
	 */
	private JButton backToHomeButton;

	/**
	 * {@link ViewerMeetViewUi} constructor
	 *
	 * @param dimension      Dimension of JPanel
	 * @param cardLayout     {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ViewerMeetViewUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		centerPanel.add(new MeetTree(MeetManagerFactory.getManager().getMeets(), new Dimension(520, 330)));

		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}
}
