package com.barattoManager.ui.components;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.EmptyStringException;
import com.barattoManager.ui.panels.optionPane.RegisterNewUserPanel;
import com.barattoManager.user.UserManager;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the Viewer home view
 */
public class ViewerHomeUi extends JPanel {
	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * JButton used to
	 */
	private JButton btn1;
	/**
	 * JButton used to
	 */
	private JButton bt2;

	/**
	 * {@link ViewerHomeUi} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public ViewerHomeUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);

	}
}
