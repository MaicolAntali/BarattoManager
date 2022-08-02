package com.barattoManager.ui.mvc.login;

import com.barattoManager.ui.mvc.base.BaseController;
import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.ui.mvc.base.BaseView;

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
