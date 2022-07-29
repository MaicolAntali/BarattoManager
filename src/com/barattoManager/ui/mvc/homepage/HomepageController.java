package com.barattoManager.ui.mvc.homepage;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.services.user.UserManagerFactory;
import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;

public class HomepageController implements BaseController, HomepageLoginButtonListener, HomepageRegisterButtonListener {

	private final HomepageView view;

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
		try {
			UserManagerFactory.getManager().addNewUser("Maicol", "123", false);
		} catch (AlreadyExistException e) {
			throw new RuntimeException(e);
		}
		System.out.println("REGISTRAZIONE AVVENUTA, (FORSE)");
	}
}
