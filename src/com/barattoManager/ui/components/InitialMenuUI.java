package com.barattoManager.ui.components;

import com.barattoManager.ui.customComponents.optionPane.RegisterNewUserPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel that represent the initial view
 */
public class InitialMenuUI extends JPanel {

	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * JButton used to log in
	 */
	private JButton loginButton;
	/**
	 * JButton used to register new users
	 */
	private JButton RegisterButton;

	/**
	 * {@link InitialMenuUI} constructor
	 *
	 * @param dimension    Dimension of JPanel
	 * @param cardLayout   {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param contentPanel {@link JPanel} object that contains every cards
	 */
	public InitialMenuUI(Dimension dimension, CardLayout cardLayout, JPanel contentPanel) {
		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);


		loginButton.addActionListener(e -> cardLayout.show(contentPanel, ComponentsName.LOGIN.toString()));
		RegisterButton.addActionListener(e -> new RegisterNewUserPanel(mainPanel, false).createNewUser());
	}
}
