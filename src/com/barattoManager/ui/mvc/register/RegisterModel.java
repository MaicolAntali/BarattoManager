package com.barattoManager.ui.mvc.register;

import com.barattoManager.ui.mvc.Model;
import com.barattoManager.ui.mvc.login.LoginController;
import com.barattoManager.utils.AppConfigurator;

/**
 * Model of {@link RegisterController} that contains the data
 */
public class RegisterModel implements Model {

	private final String defaultPassword;
	private final boolean isConfigurator;
	private String username;

	/**
	 * Constructor  of the class
	 */
	public RegisterModel() {
		this.defaultPassword = AppConfigurator.getInstance().getPasswordSetting("default_pwd");
		this.isConfigurator = false;
		this.username = "";
	}

	/**
	 * Constructor  of the class
	 * @param isConfigurator {@link RegisterModel} used to define whether the user is a configurator or a viewer
	 */
	public RegisterModel(boolean isConfigurator) {
		this.defaultPassword = AppConfigurator.getInstance().getPasswordSetting("default_pwd");
		this.isConfigurator = isConfigurator;
		this.username = "";
	}

	/**
	 * Method used to get the default password as a {@link String}
	 * @return defaultPassword
	 */
	public String getDefaultPassword() {
		return defaultPassword;
	}

	/**
	 * Method used to get the isConfigurator as a {@link Boolean}
	 * @return isConfigurator
	 */
	public boolean isConfigurator() {
		return isConfigurator;
	}

	/**
	 * Method used to get the username as a {@link String}
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Method used to set the username
	 * @param username {@link String}
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
