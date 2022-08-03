package com.barattoManager.services.user;

import com.barattoManager.utils.AppConfigurator;
import com.barattoManager.utils.SHA512;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class User {

	@JsonProperty("username")
	private final String username;
	@JsonProperty("password")
	private String password;
	@JsonProperty("configurator")
	private final boolean isConfigurator;


	public User(
			@JsonProperty("username") String username,
			@JsonProperty("password") String password,
			@JsonProperty("is_configurator") boolean isConfigurator
	) {

		assert username.isBlank() : "Pre-condition: User username is blank";
		assert password.isBlank() : "Pre-condition: User password is blank";

		this.username = username;
		this.password = SHA512.hash(password);
		this.isConfigurator = isConfigurator;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	@JsonProperty("configurator")
	public boolean isConfigurator() {
		return isConfigurator;
	}

	public void setPassword(String password) {
		this.password = SHA512.hash(password);
	}

	@JsonIgnore
	public boolean isPasswordValid() {
		return !Objects.equals(this.password, AppConfigurator.getInstance().getPasswordSetting("default_pwd")) && this.password.trim().length() >= 5;
	}
}
