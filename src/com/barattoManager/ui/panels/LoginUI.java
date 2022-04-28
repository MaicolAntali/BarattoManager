package com.barattoManager.ui.panels;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.PasswordNotMatch;
import com.barattoManager.exception.UserNotFound;
import com.barattoManager.ui.components.SetNewPassword;
import com.barattoManager.user.UserManager;
import com.barattoManager.user.configurator.Configurator;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoginUI extends JPanel {

    public static final String SET_NEW_PASSWORD_TITLE = "seleziona una nuova password";
    public static final String ERROR_PASSWORD_NOT_VALID = "La nuova password non è valida.\n Inserisci una password diversa da: 123 e lunga almeno 5 caratteri.";
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
		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, PanelName.INITIAL_PANEL.toString()));

		// Login Listener
		loginButton.addActionListener(e -> {
			try {
				var user = userManager.checkCredential(usernameField.getText(), String.valueOf(passwordField.getPassword()));


				// check if the user need to update password
				if (Objects.equals(user.getPassword(), AppConfigurator.getInstance().getPasswordSetting("default_psw"))) {
					var setNewPassword = new SetNewPassword();
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
					cardLayout.show(panelContainer, PanelName.CONF_HOME.toString());
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
