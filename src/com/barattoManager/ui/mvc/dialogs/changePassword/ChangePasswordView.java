package com.barattoManager.ui.mvc.dialogs.changePassword;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerField;
import com.barattoManager.ui.mvc.View;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordView implements View {

	public static final String LABEL_CHANGE_YOUR_PASSWORD = "Cambia la tua password di accesso.";
	public static final String LABEL_NEW_PASSWORD = "Nuova Password";

	private final JPanel mainPanel;
	@DocumentListenerField
	private final JPasswordField passwordField;

	public ChangePasswordView() {
		this.mainPanel = new JPanel();
		this.passwordField = new JPasswordField(13);

		this.mainPanel.setLayout(new GridLayout(0, 1));
		this.mainPanel.add(new JLabel(LABEL_CHANGE_YOUR_PASSWORD));

		var pwdPanel = new JPanel();
		pwdPanel.add(new JLabel(LABEL_NEW_PASSWORD));
		pwdPanel.add(passwordField);

		this.mainPanel.add(pwdPanel);
	}

	@Override
	public JPanel getMainJPanel() {
		return mainPanel;
	}

	public String getPassword() {
		return String.valueOf(passwordField.getPassword());
	}
}
