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

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
