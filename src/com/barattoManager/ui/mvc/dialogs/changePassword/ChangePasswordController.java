package com.barattoManager.ui.mvc.dialogs.changePassword;

import com.barattoManager.ui.annotations.documentListener.DocumentListenerFor;
import com.barattoManager.ui.annotations.documentListener.DocumentListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;

public class ChangePasswordController implements Controller {

	private final ChangePasswordModel model;
	private final ChangePasswordView view;

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
	public void passwordHasChange() {
		model.setPassword(view.getPassword());
	}

	public String getPassword() {
		return model.getPassword();
	}
}
