package com.barattoManager.ui.components;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.EmptyStringException;
import com.barattoManager.exception.InvalidCredentialsException;
import com.barattoManager.ui.panels.optionPane.ChangePasswordPanel;
import com.barattoManager.user.UserManager;
import com.barattoManager.user.configurator.Configurator;
import com.barattoManager.user.viewer.Viewer;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Class used to create a JPanel that represent the registration view
 */
public class RegistrationUI extends JPanel {

	/**
	 * Title of JOptionPanel
	 */
    private static final String SET_NEW_PASSWORD_TITLE = "seleziona una nuova password";
	/**
	 * Password not valid error
	 */
    private static final String ERROR_PASSWORD_NOT_VALID = "La nuova password non è valida.\n Inserisci una password diversa da: %s e lunga almeno 5 caratteri.".formatted(AppConfigurator.getInstance().getPasswordSetting("default_pwd"));
	/**
	 * Title of Error
	 */
    private static final String ERROR_TITLE = "Errore";
	/**
	 * {@link UserManager} Object
	 */
    private final UserManager userManager;
	/**
	 * Main JPanel
	 */
	private JPanel mainPanel;
	/**
	 * Username field
	 */
	private JTextField usernameField;
	/**
	 * JButton used to go in the {@link InitialMenuUI} view
	 */
	private JButton backToInitButton;
	/**
	 * JButton used to register
	 */
	private JButton RegistrationButton;
	/**
	 * Password field
	 */
	private JPasswordField passwordField;

	/**
	 * {@link RegistrationUI} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
	public RegistrationUI(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// Get the userManager
		this.userManager = UserManager.getInstance();

		// JPanel conf
		setVisible(true);
		add(mainPanel);

		mainPanel.setPreferredSize(dimension);
		backToInitButton.addActionListener(e -> cardLayout.show(panelContainer, ComponentsName.INITIAL_PANEL.toString()));

		// Registration Listener
		RegistrationButton.addActionListener(e -> {
			try {
				userManager.addNewUser(usernameField.getText(), String.valueOf(passwordField.getPassword()), false);
				JOptionPane.showMessageDialog(this, "l'utente è stato registrato con successo", "Registrazione effetuata",JOptionPane.INFORMATION_MESSAGE);
				cardLayout.show(panelContainer, ComponentsName.INITIAL_PANEL.toString());
			} catch (AlreadyExistException | EmptyStringException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
