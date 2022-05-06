package com.barattoManager.ui.panels.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a Jpanel used to register a new configurator
 */
public class RegisterNewConfiguratorPanel extends JPanel {

	/**
	 * Username field
	 */
	private final JTextField usernameField = new JTextField(13);

	/**
	 * {@link RegisterNewConfiguratorPanel} constructor
	 */
	public RegisterNewConfiguratorPanel() {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel("Registra un nuovo configuratore"));

		var namePanel = new JPanel();
		namePanel.add(new JLabel("Username:"));
		namePanel.add(usernameField);
		mainPanel.add(namePanel);

		setVisible(true);
		add(mainPanel);
	}

	/**
	 * Method used rto get the {@link #usernameField}
	 * @return {@link JTextField} obejct
	 */
	public JTextField getUsernameField() {
		return usernameField;
	}
}
