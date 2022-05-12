package com.barattoManager.ui.panels.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel used to register a new user
 */
public class RegisterNewUserPanel extends JPanel {

	/**
	 * Username field
	 */
	private final JTextField usernameField = new JTextField(13);

	/**
	 * {@link RegisterNewUserPanel} constructor
	 */
	public RegisterNewUserPanel() {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel("Registra un nuovo utente"));

		var namePanel = new JPanel();
		namePanel.add(new JLabel("Scegli un username:"));
		namePanel.add(usernameField);
		mainPanel.add(namePanel);

		setVisible(true);
		add(mainPanel);
	}

	/**
	 * Method used to get the {@link #usernameField}
	 * @return {@link JTextField} object
	 */
	public JTextField getUsernameField() {
		return usernameField;
	}
}
