package com.barattoManager.ui.mvc.login;

import com.barattoManager.exception.InvalidCredentialException;
import com.barattoManager.services.Store;
import com.barattoManager.services.user.User;
import com.barattoManager.services.user.UserManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.configurator.homepage.ConfiguratorHomepageController;
import com.barattoManager.ui.mvc.configurator.homepage.ConfiguratorHomepageView;
import com.barattoManager.ui.mvc.dialogs.changePassword.ChangePasswordController;
import com.barattoManager.ui.mvc.dialogs.changePassword.ChangePasswordModel;
import com.barattoManager.ui.mvc.dialogs.changePassword.ChangePasswordView;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.viewer.homepage.ViewerHomepageController;
import com.barattoManager.ui.mvc.viewer.homepage.ViewerHomepageView;
import com.barattoManager.ui.utils.ControllerNames;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;

public class LoginController implements Controller {
	private final LoginModel model;
	private final LoginView view;

	public LoginController(LoginModel model, LoginView view) {
		this.model = model;
		this.view = view;

		ActionListenerInstaller.processAnnotations(this, view);
		DocumentListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public View getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "abortButton")
	private void clickOnAbort() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE.toString());
	}

	@ActionListenerFor(sourceField = "loginButton")
	private void clickOnLogin() {
		try {
			var user = UserManagerFactory.getManager()
					.loginUser(model.getUsername(), model.getPassword());

			Store.setLoggedUser(user);

			if (!user.isPasswordValid())
				changePassword();

			if (user.isPasswordValid())
				if (user.isConfigurator()) {
					RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
							new ConfiguratorHomepageController(new ConfiguratorHomepageView()),
							ControllerNames.HOMEPAGE_CONFIGURATOR.toString()
					);
					ShowControllerHandlerFactory.getHandler().fireShowListeners(
							ControllerNames.HOMEPAGE_CONFIGURATOR.toString());
				}
				else {
					RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
							new ViewerHomepageController(new ViewerHomepageView()),
							ControllerNames.HOMEPAGE_VIEWER.toString()
					);
					ShowControllerHandlerFactory.getHandler().fireShowListeners(
							ControllerNames.HOMEPAGE_VIEWER.toString());
				}

		} catch (InvalidCredentialException e) {
			new MessageDialogDisplay()
					.setParentComponent(view.getMainJPanel())
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage(e.getMessage())
					.show();
		}
	}

	@DocumentListenerFor(sourceField = "usernameField")
	private void usernameHasChange() {
		model.setUsername(view.getUsername());
	}

	@DocumentListenerFor(sourceField = "passwordField")
	private void passwordHasChange() {
		model.setPassword(view.getPassword());
	}

	private void changePassword() {
		var changePasswordController = new ChangePasswordController(
				new ChangePasswordModel(),
				new ChangePasswordView()
		);

		do {

			var option = new OptionDialogDisplay()
					.setParentComponent(view.getMainJPanel())
					.setMessage(changePasswordController.getView().getMainJPanel())
					.setTitle("Cambiare Password")
					.setMessageType(JOptionPane.QUESTION_MESSAGE)
					.show();

			if (option == JOptionPane.CANCEL_OPTION)
				break;

			if (User.isDefaultPassword(changePasswordController.getPassword()))
				new MessageDialogDisplay()
						.setParentComponent(view.getMainJPanel())
						.setMessage("La password impostata non è valida. Riprovare")
						.setTitle("Password NON valida")
						.setMessageType(JOptionPane.ERROR_MESSAGE)
						.show();
			else
				UserManagerFactory.getManager().setUserPassword(
						Store.getLoggedUser(),
						changePasswordController.getPassword()
				);

		} while (User.isDefaultPassword(changePasswordController.getPassword()));
	}
}