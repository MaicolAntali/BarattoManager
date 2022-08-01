package com.barattoManager.ui.mvc.registerUser;

import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.utils.AppConfigurator;

public class RegisterUserModel implements BaseModel {

	private final String defaultPassword;
	private String username;


	public RegisterUserModel() {
		this.defaultPassword = AppConfigurator.getInstance().getPasswordSetting("default_pwd");
		this.username = "";
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
