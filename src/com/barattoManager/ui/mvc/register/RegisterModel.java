package com.barattoManager.ui.mvc.register;

import com.barattoManager.ui.mvc.base.BaseModel;
import com.barattoManager.utils.AppConfigurator;

public class RegisterModel implements BaseModel {

	private final String defaultPassword;
	private String username;


	public RegisterModel() {
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
