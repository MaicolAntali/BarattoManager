package com.barattoManager.ui.mvc.register;

import com.barattoManager.ui.mvc.Model;
import com.barattoManager.utils.AppConfigurator;

public class RegisterModel implements Model {

	private final String defaultPassword;
	private final boolean isConfigurator;
	private String username;

	public RegisterModel() {
		this.defaultPassword = AppConfigurator.getInstance().getPasswordSetting("default_pwd");
		this.isConfigurator = false;
		this.username = "";
	}

	public RegisterModel(boolean isConfigurator) {
		this.defaultPassword = AppConfigurator.getInstance().getPasswordSetting("default_pwd");
		this.isConfigurator = isConfigurator;
		this.username = "";
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public boolean isConfigurator() {
		return isConfigurator;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
