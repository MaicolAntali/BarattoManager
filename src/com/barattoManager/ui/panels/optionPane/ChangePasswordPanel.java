package com.barattoManager.ui.panels.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a Jpanel used to change a user password
 */
public class ChangePasswordPanel extends JPanel {
	/**
	 * Password field
	 */
	private final JPasswordField passwordField = new JPasswordField(13);

	/**
	 * {@link ChangePasswordPanel} constructor
	 */
	public ChangePasswordPanel() {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel("Cambia la tua password di accesso."));

		var namePanel = new JPanel();
		namePanel.add(new JLabel("Nuova Password:"));
		namePanel.add(passwordField);
		mainPanel.add(namePanel);

		setVisible(true);
		add(mainPanel);
	}

	/**
	 * Method used to get the {@link #passwordField}
	 * @return The {@link #passwordField}
	 */
	public JPasswordField getPasswordField() {
		return passwordField;
	}
}
