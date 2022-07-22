package com.barattoManager.old.ui.customComponents.optionPane;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a {@link JPanel} used to change a user password
 */
public class ChangePasswordPanel extends JPanel {

	private static final String LABEL_CHANGE_YOUR_PASSWORD = "Cambia la tua password di accesso.";
	private static final String LABEL_NEW_PASSWORD = "Nuova Password:";
	private final JPasswordField passwordField = new JPasswordField(13);

	/**
	 * Constructor of the class
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
	 * Method used to get {@link JPasswordField} with the user password
	 *
	 * @return {@link JPasswordField} with the user password
	 */
	public JPasswordField getPasswordField() {
		return passwordField;
	}
}
