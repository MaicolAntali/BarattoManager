package com.barattoManager.services.user;

import com.barattoManager.utils.AppConfigurator;
import com.barattoManager.utils.SHA256;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class that represent a user object
 */
public class User {

	private static final String PRE_CONDITION_USER_USERNAME_IS_BLANK = "Pre-condition: User username is blank";
	private static final String PRE_CONDITION_USER_PASSWORD_IS_BLANK = "Pre-condition: User password is blank";
	@JsonProperty("username")
	private final String username;
	@JsonProperty("configurator")
	private final boolean isConfigurator;
	@JsonProperty("password")
	private byte[] password;


	public User(String username, String password, boolean isConfigurator) {

		assert !username.isBlank() : PRE_CONDITION_USER_USERNAME_IS_BLANK;
		assert !password.isBlank() : PRE_CONDITION_USER_PASSWORD_IS_BLANK;

		this.username = username;
		this.password = SHA256.hash(password);
		this.isConfigurator = isConfigurator;
	}

	public User(
			@JsonProperty("username") String username,
			@JsonProperty("password") byte[] password,
			@JsonProperty("is_configurator") boolean isConfigurator
	) {
		this.username = username;
		this.password = password;
		this.isConfigurator = isConfigurator;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	public byte[] getPassword() {
		return this.password;
	}

	@JsonProperty("configurator")
	public boolean isConfigurator() {
		return isConfigurator;
	}

	public void setPassword(String password) {
		this.password = SHA256.hash(password);
	}

	@JsonIgnore
	public boolean isPasswordValid() {
		return !Arrays.equals(this.password, SHA256.hash(AppConfigurator.getInstance().getPasswordSetting("default_pwd")));
	}

	public static boolean isDefaultPassword(String password) {
		return Objects.equals(password, AppConfigurator.getInstance().getPasswordSetting("default_pwd"));
	}
}
