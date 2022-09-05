package com.barattoManager.ui.mvc.dialogs.changePassword;

import com.barattoManager.ui.mvc.Model;

public class ChangePasswordModel implements Model {

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
