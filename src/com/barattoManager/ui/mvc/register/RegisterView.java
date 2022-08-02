package com.barattoManager.ui.mvc.register;

import com.barattoManager.ui.annotations.actionListener.ActionListenerField;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.base.BaseView;

import javax.swing.*;

public class RegisterView extends JPanel implements BaseView {
	private JPanel mainPanel;
	@ActionListenerField
	private JButton registerButton;
	@ActionListenerField
	private JButton abortButton;
	@DocumentListenerField
	private JTextField usernameField;

	public RegisterView() {
		setSize(PANEL_DIMENSION);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public String getUsername() {
		return usernameField.getText();
	}
}
