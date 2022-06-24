package com.barattoManager.ui.components;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.exception.InvalidCredentialsException;
import com.barattoManager.manager.UserManager;
import com.barattoManager.model.user.configurator.Configurator;
import com.barattoManager.ui.BarattoManagerGui;
import com.barattoManager.ui.components.configurator.ConfiguratorCategoryEditorUi;
import com.barattoManager.ui.components.configurator.ConfiguratorHomeUi;
import com.barattoManager.ui.components.configurator.ConfiguratorMeetEditorUi;
import com.barattoManager.ui.components.viewer.*;
import com.barattoManager.ui.customComponents.optionPane.ChangePasswordPanel;
import com.barattoManager.utils.AppConfigurator;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Class used to create a JPanel that represent the login view
 */
public class LoginUI extends JPanel {

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
	 * JButton used to log in
	 */
	private JButton loginButton;
	/**
	 * Password field
	 */
	private JPasswordField passwordField;

	/**
	 * {@link LoginUI} constructor
	 * @param dimension Dimension of JPanel
	 * @param cardLayout {@link CardLayout} object instanced in {@link com.barattoManager.ui.BarattoManagerGui}
	 * @param panelContainer {@link JPanel} object that contains every cards
	 */
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
					var setNewPasswordUi = new ChangePasswordPanel();
					var isValidPassword = false;
					do {
						int result = JOptionPane.showOptionDialog(
								this,
								setNewPasswordUi,
                                SET_NEW_PASSWORD_TITLE,
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								null,
								null
						);

						if (result == JOptionPane.OK_OPTION) {
							var password = String.valueOf(setNewPasswordUi.getPasswordField().getPassword());
							isValidPassword = user.isPasswordValid(password);

							if (!isValidPassword) {
								JOptionPane.showMessageDialog(
										this,
                                        ERROR_PASSWORD_NOT_VALID,
                                        ERROR_TITLE,
										JOptionPane.ERROR_MESSAGE
								);
							}
							else {
								userManager.setUserPassword(user, password);
							}
						}

						if (result == JOptionPane.CANCEL_OPTION) {
							break;
						}

					} while (!isValidPassword);
				}

				if (user.isPasswordValid(user.getPassword())) {
					if (user instanceof Configurator) {
						panelContainer.add(new ConfiguratorHomeUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.CONF_HOME.toString());
						panelContainer.add(new ConfiguratorCategoryEditorUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.CONF_CATEGORY_EDITOR.toString());
						panelContainer.add(new ConfiguratorMeetEditorUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.CONF_MEET_EDITOR.toString());

						cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString());
					}
					else {
						panelContainer.add(new ViewerHomeUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_HOME.toString());
						panelContainer.add(new ViewerCategoryViewUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_CATEGORY.toString());
						panelContainer.add(new ViewerMeetViewUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_MEET.toString());

						// add Listeners
						var viewerStore =  new ViewerStoreArticle(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer, user);
						EventFactory.getArticlesEvent().addListener(viewerStore);

						var viewerDashboard = new ViewerDashboardArticle(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer, user);
						EventFactory.getArticlesEvent().addListener(viewerDashboard);

						panelContainer.add(viewerStore, ComponentsName.VIEWER_STORE_ARTICLES.toString());
						panelContainer.add(viewerDashboard, ComponentsName.VIEWER_YOUR_ARTICLES.toString());
						panelContainer.add(new ViewerExchangesViewUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer, user), ComponentsName.VIEWER_EXCHANGES.toString());

						cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString());
					}
				}

			} catch (InvalidCredentialsException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
