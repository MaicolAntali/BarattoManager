package com.barattoManager.ui.mvc.registerUser;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.services.user.UserManagerFactory;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.utils.messageDialog.MessageDialogDisplay;

import javax.swing.*;

public class RegisterUserController
		implements BaseController, RegisterUserAbortButtonListener, RegisterUserRegisterButtonListener, RegistrerUserUsernameTextFieldUpdateListener {

	private final RegisterUserModel model;
	private final RegisterUserView view;

	public RegisterUserController(RegisterUserModel model, RegisterUserView view) {
		this.model = model;
		this.view = view;

		this.view.addAbortButtonListeners(this);
		this.view.addRegisterButtonListeners(this);
		this.view.addUsernameTextFieldUpdateListeners(this);
	}

	@Override
	public BaseModel getModel() {
		return model;
	}

	@Override
	public BaseRegisterUserView getView() {
		return view;
	}

	@Override
	public void clickOnAbort() {
		ShowControllerHandlerFactory.getHandler().fireShowListeners("homepage");
	}

	@Override
	public void clickOnRegister() {
		try {
			UserManagerFactory.getManager()
					.addNewUser(
							model.getUsername(),
							model.getDefaultPassword(),
							false
					);
		} catch (AlreadyExistException e) {
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

	@Override
	public void usernameFieldHasChange(String usernameUpdated) {
		model.setUsername(usernameUpdated);
	}
}
