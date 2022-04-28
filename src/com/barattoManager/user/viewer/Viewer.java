package com.barattoManager.user.viewer;

import com.barattoManager.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Viewer")
public class Viewer extends User {

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;


	public Viewer(
			@JsonProperty("username") String username,
			@JsonProperty("password") String password
	) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String getChildType() {
		return "Viewer";
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}
}
