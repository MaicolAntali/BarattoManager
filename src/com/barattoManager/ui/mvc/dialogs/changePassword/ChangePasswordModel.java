package com.barattoManager.ui.mvc.dialogs.changePassword;

import com.barattoManager.ui.mvc.Model;

/**
 * Model of {@link ChangePasswordModel} that contains the data
 */
public class ChangePasswordModel implements Model {

	private String password;

	/**
	 * Constructor of the class
	 */
	public ChangePasswordModel() {
		this.password = "";
	}

	/**
	 * Method used to get the password as a {@link String}
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method used to set the password
	 * @param password {@link String}
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
