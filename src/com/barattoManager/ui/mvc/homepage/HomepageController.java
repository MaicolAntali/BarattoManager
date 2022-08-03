package com.barattoManager.ui.mvc.homepage;

import com.barattoManager.ui.annotations.actionListener.ActionListenerFor;
import com.barattoManager.ui.annotations.actionListener.ActionListenerInstaller;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;
import com.barattoManager.ui.mvc.login.LoginController;
import com.barattoManager.ui.mvc.login.LoginModel;
import com.barattoManager.ui.mvc.login.LoginView;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.register.RegisterController;
import com.barattoManager.ui.mvc.register.RegisterModel;
import com.barattoManager.ui.mvc.register.RegisterView;

public class HomepageController implements BaseController {

	private final HomepageView view;

	public HomepageController(HomepageView view) {
		this.view = view;

		ActionListenerInstaller.processAnnotations(this, view);
	}

	@Override
	public BaseModel getModel() {
		return null;
	}

	@Override
	public BaseView getView() {
		return view;
	}


	@ActionListenerFor(sourceField = "loginButton")
	public void clickOnLogin() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new LoginController(new LoginModel(), new LoginView()), "login-user"
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners("login-user");
	}

	@ActionListenerFor(sourceField = "registerButton")
	public void clickOnRegister() {
		RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(
				new RegisterController(new RegisterModel(), new RegisterView()), "register-user"
		);
		ShowControllerHandlerFactory.getHandler().fireShowListeners("register-user");
	}
}
