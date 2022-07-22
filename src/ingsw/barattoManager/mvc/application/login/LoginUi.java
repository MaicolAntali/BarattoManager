package ingsw.barattoManager.mvc.application.login;

import com.barattoManager.event.DataChangeListener;
import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.old.exception.InvalidCredentialsException;
import com.barattoManager.old.sample.user.Configurator;
import com.barattoManager.old.sample.user.User;
import com.barattoManager.old.ui.components.ComponentsName;
import com.barattoManager.old.ui.components.configurator.ConfiguratorCategoryEditorUi;
import com.barattoManager.old.ui.components.configurator.ConfiguratorHomeUi;
import com.barattoManager.old.ui.components.configurator.ConfiguratorMeetEditorUi;
import com.barattoManager.old.ui.components.configurator.ConfiguratorOfferView;
import com.barattoManager.old.ui.components.viewer.*;
import com.barattoManager.old.ui.customComponents.optionPane.ChangePasswordPanel;
import com.barattoManager.old.utils.AppConfigurator;
import ingsw.barattoManager.mvc.application.main.ApplicationUi;
import ingsw.barattoManager.mvc.models.UserModel;
import ingsw.barattoManager.mvc.models.factories.UserModelFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class used to create a {@link JPanel} that allows the users to login
 */
public class LoginUi extends JPanel implements DataChangeListener<String, User> {
	private static final String SET_NEW_PASSWORD_TITLE = "seleziona una nuova password";
	private static final String ERROR_PASSWORD_NOT_VALID = "La nuova password non è valida.\n Inserisci una password diversa da: %s e lunga almeno 5 caratteri.".formatted(AppConfigurator.getInstance().getPasswordSetting("default_pwd"));
	private static final String ERROR_TITLE = "Errore";
	private final UserModel userManager;

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
	public LoginUi(Dimension dimension, CardLayout cardLayout, JPanel panelContainer) {
		// Get the userManager
		this.userManager = UserModelFactory.getManager();

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
						var configuratorHome       = new ConfiguratorHomeUi(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer);
						var configuratorCategories = new ConfiguratorCategoryEditorUi(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer);
						var configuratorMeets      = new ConfiguratorMeetEditorUi(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer);
						var configuratorOfferView  = new ConfiguratorOfferView(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer);

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
						var viewerStore     = new ViewerStoreArticle(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer, user);
						var viewerDashboard = new ViewerDashboardArticle(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer, user);
						var viewerExchange  = new ViewerExchangesViewUi(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer, user);

						// add events
						EventFactory.getArticlesEvent().addListener(viewerStore);
						EventFactory.getArticlesEvent().addListener(viewerDashboard);
						EventFactory.getTradesEvent().addListener(viewerExchange);

						panelContainer.add(viewerStore, ComponentsName.VIEWER_STORE_ARTICLES.toString());
						panelContainer.add(viewerDashboard, ComponentsName.VIEWER_YOUR_ARTICLES.toString());
						panelContainer.add(viewerExchange, ComponentsName.VIEWER_EXCHANGES.toString());
						panelContainer.add(new ViewerHomeUi(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_HOME.toString());
						panelContainer.add(new ViewerCategoryViewUi(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_CATEGORY.toString());
						panelContainer.add(new ViewerMeetViewUi(ApplicationUi.CONTENT_PANEL_DEFAULT_DIMENSION, cardLayout, panelContainer), ComponentsName.VIEWER_MEET.toString());


						cardLayout.show(panelContainer, ComponentsName.VIEWER_HOME.toString());
					}
				}

			} catch (InvalidCredentialsException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	@Override
	public void update(ConcurrentHashMap<String, User> updatedMap) {
		System.out.println("Login data updated.");
	}
}
