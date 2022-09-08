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
	 * Method used to get the username as a {@link String}
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Method used to get the password as a {@link String}
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method used to set the username
	 * @param username {@link String}
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Method used to set the password
	 * @param password {@link String}
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
