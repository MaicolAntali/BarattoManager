package com.barattoManager.ui.mvc.login;

import com.barattoManager.exception.InvalidCredentialException;
import com.barattoManager.services.Store;
import com.barattoManager.services.user.User;
import com.barattoManager.services.user.UserManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.changePassword.ChangePasswordController;
import com.barattoManager.ui.utils.changePassword.ChangePasswordModel;
import com.barattoManager.ui.utils.changePassword.ChangePasswordView;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;
import com.barattoManager.ui.utils.optionDialog.OptionDialogDisplay;

import javax.swing.*;

public class LoginController implements BaseController {
	private final LoginModel model;
	private final LoginView view;

	public LoginController(LoginModel model, LoginView view) {
		this.model = model;
		this.view = view;

		ActionListenerInstaller.processAnnotations(this, view);
		DocumentListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public BaseModel getModel() {
		return model;
	}

	@Override
	public BaseView getView() {
		return view;
	}

	@ActionListenerFor(sourceField = "abortButton")
	private void clickOnAbort() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners("homepage");
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
				if (user.isConfigurator())
					System.out.println("LOGIN ESEGUITO COME CONFIUGURATORE");
				else
					System.out.println("LOGIN ESEGUITO COME FRUITORE");

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

			if (!User.checkPassword(changePasswordController.getPassword()))
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

		} while (!User.checkPassword(changePasswordController.getPassword()));
	}

}