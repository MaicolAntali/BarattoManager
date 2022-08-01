package com.barattoManager.ui.mvc.loginUser;

import com.barattoManager.ui.mvc.base.BaseView;

public interface BaseLoginUserView extends BaseView {
    void addLoginButtonListener(LoginUserLoginButtonListener listener);
}
