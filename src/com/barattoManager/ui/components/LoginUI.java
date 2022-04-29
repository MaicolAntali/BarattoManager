package com.barattoManager.ui.components;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.PasswordNotMatch;
import com.barattoManager.exception.UserNotFound;
import com.barattoManager.ui.panels.optionPane.ChangePasswordPanel;
import com.barattoManager.user.UserManager;
import com.barattoManager.user.configurator.Configurator;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoginUI extends JPanel {

    public static final String SET_NEW_PASSWORD_TITLE = "seleziona una nuova password";
    public static final String ERROR_PASSWORD_NOT_VALID = "La nuova password non è valida.\n Inserisci una password diversa da: %s e lunga almeno 5 caratteri.".formatted(AppConfigurator.getInstance().getPasswordSetting("default_pwd"));
    public static final String ERROR_TITLE = "Errore";
    private final UserManager userManager;
	private JPanel mainPanel;
	private JTextField usernameField;
	private JButton backToInitButton;
	private JButton loginButton;
	private JPasswordField passwordField;

	public LoginUI(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// Get the userManager
		this.userManager = UserManager.getInstance();

		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);
		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.INITIAL_PANEL.toString()));

		// Login Listener
		loginButton.addActionListener(e -> {
			try {
				var user = userManager.checkCredential(usernameField.getText(), String.valueOf(passwordField.getPassword()));


				// check if the user need to update password
				if (Objects.equals(user.getPassword(), AppConfigurator.getInstance().getPasswordSetting("default_pwd"))) {
					var setNewPassword = new ChangePasswordPanel();
					var isValidPassword = false;
					String password = "";
					do {
						int result = JOptionPane.showOptionDialog(
								this,
								setNewPassword,
                                SET_NEW_PASSWORD_TITLE,
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								null,
								null
						);

						if (result == JOptionPane.OK_OPTION) {
							password = String.valueOf(setNewPassword.getPasswordField().getPassword());
							isValidPassword = user.isPasswordValid(password);

							if (!isValidPassword) {
								JOptionPane.showMessageDialog(
										this,
                                        ERROR_PASSWORD_NOT_VALID,
                                        ERROR_TITLE,
										JOptionPane.ERROR_MESSAGE
								);
							}
						}

					} while (!isValidPassword);

					userManager.setUserPassword(user, password);
				}

				if (user instanceof Configurator) {
					cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString());
				}
				else {
					System.out.print("VIEWER");
				}
			} catch (UserNotFound | PasswordNotMatch ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
