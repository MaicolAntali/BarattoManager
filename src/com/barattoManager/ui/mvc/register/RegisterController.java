package com.barattoManager.ui.mvc.register;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.InvalidArgumentException;
import com.barattoManager.services.user.UserManagerFactory;
import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;

public class RegisterController implements BaseController {

	private final RegisterModel model;
	private final RegisterView view;

	public RegisterController(RegisterModel model, RegisterView view) {
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
	public void clickOnAbort() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners("homepage");
	}

	@ActionListenerFor(sourceField = "registerButton")
	public void clickOnRegister() {
		try {
			UserManagerFactory.getManager()
					.addNewUser(
							model.getUsername(),
							model.getDefaultPassword(),
							false
					);
		} catch (AlreadyExistException | InvalidArgumentException e) {
			new MessageDialogDisplay()
					.setParentComponent(view)
					.setMessageType(JOptionPane.ERROR_MESSAGE)
					.setTitle("Errore")
					.setMessage(e.getMessage())
					.show();
		}

		new MessageDialogDisplay()
				.setParentComponent(view)
				.setMessage(
						"Registrazione avvenuta con successo.\n\nLo username impostato è: %s\nÈ stata impostata la password di default: %s"
								.formatted(model.getUsername(), model.getDefaultPassword()))
				.show();

		ShowControllerHandlerFactory.getHandler().fireShowListeners("homepage");
	}

	@DocumentListenerFor(sourceField = "usernameField")
	public void usernameHasChange() {
		model.setUsername(view.getUsername());
	}
}
