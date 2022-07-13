package com.barattoManager.ui.components.viewer;

import com.barattoManager.manager.factory.MeetManagerFactory;
import com.barattoManager.ui.components.ComponentsName;
import com.barattoManager.ui.customComponents.tree.meet.MeetTree;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} that allows the viewer to see the {@link com.barattoManager.model.meet.Meet meet}
 */
public class ViewerMeetViewUi extends JPanel {

	private JPanel mainPanel;
	private JPanel centerPanel;
	private JButton backToHomeButton;

	/**
	 * Constructor of the class
	 *
	 * @param dimension      {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout     {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
	 */
	public ViewerMeetViewUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		setVisible(true);
		add(mainPanel);
		mainPanel.setPreferredSize(dimension);

		centerPanel.add(new MeetTree(MeetManagerFactory.getManager().getMeets(), new Dimension(520, 330)));

		backToHomeButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString()));
	}
}
