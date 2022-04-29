package com.barattoManager.ui.panels.optionPane;

import javax.swing.*;
import java.awt.*;

public class RegisterNewConfiguratorPanel extends JPanel {

	private final JTextField usernameField = new JTextField(13);

	public RegisterNewConfiguratorPanel() {
		var mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 1));

		mainPanel.add(new JLabel("Registra un nuovo configuratore"));

		var namePanel = new JPanel();
		namePanel.add(new JLabel("Username:"));
		namePanel.add(usernameField);
		mainPanel.add(namePanel);

		setVisible(true);
		add(mainPanel);
	}

	public JTextField getUsernameField() {
		return usernameField;
	}
}
