package com.barattoManager.ui.customComponents.optionPane;

import com.barattoManager.config.AppConfigurator;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.EmptyStringException;
import com.barattoManager.user.UserManager;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a JPanel used to register a new user
 */
public class RegisterNewUserPanel {

	private static final String NEW_USER_CORRECTLY_CREATED = "Nuovo %s creato correttamente.\n\nUsername: %s\nPassword: %s";
	/**
	 * User registration panel title
	 */
	private static final String TITLE_REGISTRATION = "Registrazione";
	/**
	 * Register a new viewer label
	 */
	private static final String LABEL_REGISTER_NEW_VIEWER = "Registrazione di un nuovo fruitore";
	/**
	 * Register a new configurator label
	 */
	private static final String LABEL_REGISTER_NEW_CONFIGURATOR = "Registrazione di un nuovo configuratore";
	/**
	 * User registered message title
	 */
	private static final String TITLE_REGISTERED = "Registrazione effetuata";
	/**
	 * Choose a username label
	 */
	private static final String LABEL_CHOOSE_USERNAME= "Scegli un username:";
	/**
	 * Error
	 */
	private static final String ERROR = "Errore";
	/**
	 * Username field
	 */
	private final JTextField usernameField = new JTextField(13);
	/**
	 * mainPanel
	 */
	private final JPanel mainPanel = new JPanel();
	/**
	 * parentPanel
	 */
	private final JPanel parentPanel;
	/**
	 * true if is a configurator
	 */
	private final boolean isConfigurator;


	/**
	 * The {@link RegisterNewUserPanel} constructor
	 * @param parentPanel
	 * @param isConfigurator
	 */
	public RegisterNewUserPanel(JPanel parentPanel, boolean isConfigurator) {
		this.parentPanel = parentPanel;
		this.isConfigurator = isConfigurator;

		var panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));

		panel.add(new JLabel(isConfigurator ? LABEL_REGISTER_NEW_CONFIGURATOR : LABEL_REGISTER_NEW_VIEWER));

		var namePanel = new JPanel();
		namePanel.add(new JLabel(LABEL_CHOOSE_USERNAME));
		namePanel.add(usernameField);
		panel.add(namePanel);

		mainPanel.setVisible(true);
		mainPanel.add(panel);
	}

	/**
	 * Method used to create a new user
	 */
	public void createNewUser(){
		int result = JOptionPane.showOptionDialog(
				parentPanel,
				mainPanel,
				TITLE_REGISTRATION,
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				null
		);

		if (result == JOptionPane.OK_OPTION) {
			try {
				UserManager.getInstance().addNewUser(
						usernameField.getText(),
						AppConfigurator.getInstance().getPasswordSetting("default_pwd"),
						false
				);

				JOptionPane.showMessageDialog(
						parentPanel,
						NEW_USER_CORRECTLY_CREATED.formatted(isConfigurator? "configuratore" :  "fruitore" ,usernameField.getText(), AppConfigurator.getInstance().getPasswordSetting("default_pwd")),
						TITLE_REGISTERED,
						JOptionPane.INFORMATION_MESSAGE
				);
			} catch (AlreadyExistException | EmptyStringException ex) {
				JOptionPane.showMessageDialog(parentPanel, ex.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
