package com.barattoManager.ui.panels.optionPane;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordPanel extends JPanel {

	private final JPasswordField passwordField = new JPasswordField(13);

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

	public JPasswordField getPasswordField() {
		return passwordField;
	}
}
