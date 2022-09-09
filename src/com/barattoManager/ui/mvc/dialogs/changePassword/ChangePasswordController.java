package com.barattoManager.ui.mvc.dialogs.changePassword;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;

/**
 * Controller that handle the events of {@link ChangePasswordView} and update the data in the {@link ChangePasswordModel}
 */
public class ChangePasswordController implements Controller {

	private final ChangePasswordModel model;
	private final ChangePasswordView view;

	/**
	 * Constructor of the class
	 *
	 *@param model {@link ChangePasswordModel} represent the model of the controller
	 *@param view {@link  ChangePasswordView} represent the view of the controller
	 */
	public ChangePasswordController(ChangePasswordModel model, ChangePasswordView view) {
		this.model = model;
		this.view = view;

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

	@DocumentListenerFor(sourceField = "passwordField")
	private void passwordHasChange() {
		model.setPassword(view.getPassword());
	}

	/**
	 * Method used to get the password as a {@link String}
	 * @return password
	 */
	public String getPassword() {
		return model.getPassword();
	}
}
