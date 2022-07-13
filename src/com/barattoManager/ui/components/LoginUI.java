package com.barattoManager.ui.components;

import com.barattoManager.event.events.DataChangeListener;
import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.exception.InvalidCredentialsException;
import com.barattoManager.manager.UserManager;
import com.barattoManager.manager.factory.UserManagerFactory;
import com.barattoManager.model.article.Article;
import com.barattoManager.model.user.Configurator;
import com.barattoManager.model.user.User;
import com.barattoManager.ui.BarattoManagerGui;
import com.barattoManager.ui.components.configurator.ConfiguratorCategoryEditorUi;
import com.barattoManager.ui.components.configurator.ConfiguratorHomeUi;
import com.barattoManager.ui.components.configurator.ConfiguratorMeetEditorUi;
import com.barattoManager.ui.components.configurator.ConfiguratorOfferView;
import com.barattoManager.ui.components.viewer.*;
import com.barattoManager.ui.customComponents.optionPane.ChangePasswordPanel;
import com.barattoManager.utils.AppConfigurator;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to create a {@link JPanel} that allows the users to login
 */
public class LoginUI extends JPanel implements DataChangeListener<String, User> {
	private static final String SET_NEW_PASSWORD_TITLE = "seleziona una nuova password";
	private static final String ERROR_PASSWORD_NOT_VALID = "La nuova password non è valida.\n Inserisci una password diversa da: %s e lunga almeno 5 caratteri.".formatted(AppConfigurator.getInstance().getPasswordSetting("default_pwd"));
	private static final String ERROR_TITLE = "Errore";
	private final UserManager userManager;

	private JPanel mainPanel;

	private JTextField usernameField;

	private JButton backToInitButton;

	private JButton loginButton;

	private JPasswordField passwordField;

	/**
	 * Constructor of the class
	 *
	 * @param dimension    {@link Dimension} of the {@link JPanel} to be created
	 * @param cardLayout   {@link CardLayout} object that represent the type layout
	 * @param panelContainer {@link JPanel} object which contains all useful layouts (cards)
	 */
	public LoginUI(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// Get the userManager
		this.userManager = UserManagerFactory.getManager();

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
					var isValidPassword  = false;
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
						// create UI
						var configuratorHome       = new ConfiguratorHomeUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer);
						var configuratorCategories = new ConfiguratorCategoryEditorUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer);
						var configuratorMeets      = new ConfiguratorMeetEditorUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer);
						var configuratorOfferView  = new ConfiguratorOfferView(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer);

						// add events
						EventFactory.getCategoriesEvent().addListener(configuratorCategories);
						EventFactory.getMeetsEvent().addListener(configuratorMeets);

						panelContainer.add(configuratorHome, ComponentsName.CONF_HOME.toString());
						panelContainer.add(configuratorCategories, ComponentsName.CONF_CATEGORY_EDITOR.toString());
						panelContainer.add(configuratorMeets, ComponentsName.CONF_MEET_EDITOR.toString());
						panelContainer.add(configuratorOfferView, ComponentsName.CONF_OFFER_VIEW.toString());

						cardLayout.show(panelContainer, ComponentsName.CONF_HOME.toString());
					}
					else {
						// create UI
						var viewerStore     = new ViewerStoreArticle(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer, user);
						var viewerDashboard = new ViewerDashboardArticle(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer, user);
						var viewerExchange  = new ViewerExchangesViewUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer, user);

						// add events
						EventFactory.getArticlesEvent().addListener(viewerStore);
						EventFactory.getArticlesEvent().addListener(viewerDashboard);
						EventFactory.getTradesEvent().addListener(viewerExchange);

						panelContainer.add(viewerStore, ComponentsName.VIEWER_STORE_ARTICLES.toString());
						panelContainer.add(viewerDashboard, ComponentsName.VIEWER_YOUR_ARTICLES.toString());
						panelContainer.add(viewerExchange, ComponentsName.VIEWER_EXCHANGES.toString());
						panelContainer.add(new ViewerHomeUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_HOME.toString());
						panelContainer.add(new ViewerCategoryViewUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_CATEGORY.toString());
						panelContainer.add(new ViewerMeetViewUi(BarattoManagerGui.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_MEET.toString());


						cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString());
					}
				}

			} catch (InvalidCredentialsException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	@Override
	public void update(ConcurrentHashMap<String, User> updatedMap) {
		System.out.println("Login data updated.");
	}
}
