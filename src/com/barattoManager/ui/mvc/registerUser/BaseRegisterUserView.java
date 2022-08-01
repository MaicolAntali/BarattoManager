package com.barattoManager.ui.mvc.registerUser;

import com.barattoManager.ui.mvc.base.BaseView;

public interface BaseRegisterUserView extends BaseView {

	void addAbortButtonListeners(RegisterUserAbortButtonListener listener);
	void addRegisterButtonListeners(RegisterUserRegisterButtonListener listener);
	void addUsernameTextFieldUpdateListeners(RegistrerUserUsernameTextFieldUpdateListener listener);

}
