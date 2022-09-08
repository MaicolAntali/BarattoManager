package com.barattoManager.ui.mvc.login;

import com.barattoManager.ui.mvc.Model;

public class LoginModel implements Model {
	private String username;
	private String password;

	public LoginModel() {
		this.username = "";
		this.password = "";
	}

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
