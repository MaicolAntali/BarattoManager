package com.barattoManager.ui.mvc.dialogs.changePassword;

import com.barattoManager.ui.mvc.base.BaseModel;

public class ChangePasswordModel implements BaseModel {

	private String password;

	public ChangePasswordModel() {
		this.password = "";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
