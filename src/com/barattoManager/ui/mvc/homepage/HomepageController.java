package com.barattoManager.ui.mvc.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.Controller;
import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.View;
import com.barattoManager.ui.mvc.login.LoginController;
import com.barattoManager.ui.mvc.login.LoginModel;
import com.barattoManager.ui.mvc.login.LoginView;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.register.RegisterController;
import com.barattoManager.ui.mvc.register.RegisterModel;
import com.barattoManager.ui.mvc.register.RegisterView;
import com.barattoManager.ui.utils.ControllerNames;

public class HomepageController implements Controller {

	private final HomepageView view;

	public HomepageController(HomepageView view) {
		this.view = view;

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public Model getModel() {
		return null;
	}

	@Override
	public View getView() {
		return view;
	}


	@ActionListenerFor(sourceField = "loginButton")
	private void clickOnLogin() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new LoginController(new LoginModel(), new LoginView()), ControllerNames.LOGIN.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.LOGIN.toString());
	}

	@ActionListenerFor(sourceField = "registerButton")
	private void clickOnRegister() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new RegisterController(new RegisterModel(), new RegisterView()), ControllerNames.REGISTER_VIEWER.toString()
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners(ControllerNames.REGISTER_VIEWER.toString());
	}
}
