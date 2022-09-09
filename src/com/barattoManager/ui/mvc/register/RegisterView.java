package com.barattoManager.ui.mvc.register;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;

/**
 * View that shows the panel which is used to register
 */
public class RegisterView implements View {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton registerButton;
	@ActionListenerField
	private JButton abortButton;
	@DocumentListenerField
	private JTextField usernameField;

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
}
