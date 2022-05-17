package com.barattoManager.ui.customComponents.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel used to change a user password
 */
public class ChangePasswordPanel extends JPanel {

	/**
	 * Change your password label
	 */
	private static final String LABEL_CHANGE_YOUR_PASSWORD = "Cambia la tua password di accesso.";
	/**
	 * New password label
	 */
	private static final String LABEL_NEW_PASSWORD = "Nuova Password:";
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

		mainPanel.add(new JLabel(LABEL_CHANGE_YOUR_PASSWORD));

		var namePanel = new JPanel();
		namePanel.add(new JLabel(LABEL_NEW_PASSWORD));
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
