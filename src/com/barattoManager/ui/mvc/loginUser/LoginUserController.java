package com.barattoManager.ui.mvc.loginUser;

import com.barattoManager.ui.mvc.base.*;

public class LoginUserController implements BaseController, LoginUserLoginButtonListener {
    private final LoginUserModel model;

    public LoginUserController(LoginUserModel model) {
        this.model = model;
    }

    @Override
    public BaseModel getModel() {
        return model;
    }

    @Override
    public BaseView getView() {
        return null;
    }

    @Override
    public void clickOnLogin() {
    }
}
