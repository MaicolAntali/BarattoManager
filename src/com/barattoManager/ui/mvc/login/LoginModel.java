package com.barattoManager.ui.mvc.login;

import com.barattoManager.ui.mvc.Model;

/**
 * Model of {@link LoginController} that contains the data
 */
public class LoginModel implements Model {
	private String username;
	private String password;

	/**
	 * Constructor  of the class
	 */
	public LoginModel() {
		this.username = "";
		this.password = "";
	}

	/**
	 * Method used to get the username
	 * @return username as a {@link String}
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Method used to get the password
	 * @return password as a {@link String}
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method used to set the username
	 * @param username {@link String} to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Method used to set the password
	 * @param password {@link String} to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
