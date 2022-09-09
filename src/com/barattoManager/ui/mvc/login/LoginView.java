package com.barattoManager.ui.mvc.login;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

/**
 * View that shows the panel which is used to log-in
 */
public class LoginView implements View {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton loginButton;
	@ActionListenerField
	private JButton abortButton;
	@DocumentListenerField
	private JTextField usernameField;
	@DocumentListenerField
	private JPasswordField passwordField;

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	/**
	 * Method used to get the username from a {@link JTextField}
	 *
	 * @return username as a {@link String}
	 */
	public String getUsername() {
		return usernameField.getText();
	}

	/**
	 * Method used to get the password from a {@link JPasswordField}
	 *
	 * @return password as a {@link String}
	 */
	public String getPassword() {
		return String.valueOf(passwordField.getPassword());
	}
}
