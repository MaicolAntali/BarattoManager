package com.barattoManager.ui.mvc.register;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.user.UserManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.ControllerNames;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;

public class RegisterController implements Controller {

	private final RegisterModel model;
	private final RegisterView view;

	public RegisterController(RegisterModel model, RegisterView view) {
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
		if (model.isConfigurator())
			ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_CONFIGURATOR.toString());
		else
			ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE.toString());
	}

	@ActionListenerFor(sourceField = "registerButton")
	private void clickOnRegister() {
		try {
			UserManagerFactory.getManager()
					.addNewUser(
							model.getUsername(),
							model.getDefaultPassword(),
							model.isConfigurator()
					);

			registrationCompleted();

		} catch (AlreadyExistException | InvalidArgumentException e) {
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

	private void registrationCompleted() {
		new MessageDialogDisplay()
				.setParentComponent(view.getMainJPanel())
				.setMessage(
						"Registrazione avvenuta con successo.\n\nLo username impostato è: %s\nÈ stata impostata la password di default: %s"
								.formatted(model.getUsername(), model.getDefaultPassword()))
				.show();

		if (model.isConfigurator())
			ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE_CONFIGURATOR.toString());
		else
			ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.HOMEPAGE.toString());
	}
}
