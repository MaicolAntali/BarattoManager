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

	/**
	 * Constructor of the class
	 * @param username {@link String} represent the username
	 * @param password {@link String} represent the password of the user
	 * @param isConfigurator {@link Boolean} represent whether the user is a configurator or not
	 */
	public User(String username, String password, boolean isConfigurator) {

		assert !username.isBlank() : PRE_CONDITION_USER_USERNAME_IS_BLANK;
		assert !password.isBlank() : PRE_CONDITION_USER_PASSWORD_IS_BLANK;

		this.username = username;
		this.password = SHA256.hash(password);
		this.isConfigurator = isConfigurator;
	}

	/**
	 * Constructor of the class
	 * @param username {@link String} represent the username
	 * @param password {@link String} represent the password of the user
	 * @param isConfigurator {@link Boolean} represent whether the user is a configurator or not
	 */
	public User(
			@JsonProperty("username") String username,
			@JsonProperty("password") byte[] password,
			@JsonProperty("is_configurator") boolean isConfigurator
	) {
		this.username = username;
		this.password = password;
		this.isConfigurator = isConfigurator;
	}

	/**
	 * Method used to get username
	 * @return {@link String} of username
	 */
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	/**
	 * Method used to get the password
	 * @return {@code byte} array
	 */
	@JsonIgnore
	public byte[] getPassword() {
		return this.password;
	}

	/**
	 * Method used to get true if the {@link User} is a configurator
	 * @return True if the {@link User} is a configurator
	 */
	@JsonProperty("configurator")
	public boolean isConfigurator() {
		return isConfigurator;
	}

	/**
	 * Method used to set a password
	 * @param password {@link String} password coded
	 */
	public void setPassword(String password) {
		this.password = SHA256.hash(password);
	}

	/**
	 * Method used to check if the password is valid
	 * @return true if is valid
	 */
	@JsonIgnore
	public boolean isPasswordValid() {
		return !Arrays.equals(this.password, SHA256.hash(AppConfigurator.getInstance().getPasswordSetting("default_pwd")));
	}

	/**
	 * Method used to get whether the password is a default or not
	 * @param password {@link String} represent the password of the user
	 * @return {@link Boolean}
	 */
	public static boolean isDefaultPassword(String password) {
		return Objects.equals(password, AppConfigurator.getInstance().getPasswordSetting("default_pwd"));
	}
}
