package com.barattoManager.ui.mvc.homepage;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.mainFrame.events.RegisterControllerHandlerFactory;
import com.barattoManager.ui.mvc.mainFrame.events.ShowControllerHandlerFactory;
import com.barattoManager.ui.mvc.registerUser.RegisterUserController;
import com.barattoManager.ui.mvc.registerUser.RegisterUserModel;
import com.barattoManager.ui.mvc.registerUser.RegisterUserView;

public class HomepageController implements BaseController, HomepageLoginButtonListener, HomepageRegisterButtonListener {

	private final HomepageView view;

	private RegisterUserController registerUserController;

	public HomepageController(HomepageView view) {
		this.view = view;
		this.view.addLoginButtonListeners(this);
		this.view.addRegisterButtonListeners(this);
	}

	@Override
	public BaseModel getModel() {
		return null;
	}

	@Override
	public BaseHomepageView getView() {
		return view;
	}

	@Override
	public void clickOnLogin() {
		System.out.println("LOGIN");
	}

	@Override
	public void clickOnRegister() {
		if (registerUserController == null) {
			registerUserController = new RegisterUserController(new RegisterUserModel(), new RegisterUserView());
			RegisterControllerHandlerFactory.getHandler().fireRegisterListeners(registerUserController, "register-user");
		}

		ShowControllerHandlerFactory.getHandler().fireShowListeners("register-user");
	}
}
