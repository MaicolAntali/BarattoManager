package com.barattoManager.ui.mvc.login;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

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

	public String getUsername() {
		return usernameField.getText();
	}

	public String getPassword() {
		return String.valueOf(passwordField.getPassword());
	}
}
