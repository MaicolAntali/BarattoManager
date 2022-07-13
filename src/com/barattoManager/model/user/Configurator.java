package com.barattoManager.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Class that represent the Configurator {@code extends User}
 */
@JsonTypeName("Configurator")
public final class Configurator extends User {

	private static final String PRE_CONDITION_CONFIGURATOR_USERNAME_IS_BLANK = "Pre-condition: Configurator username is blank";
	private static final String PRE_CONDITION_CONFIGURATOR_PASSWORD_IS_BLANK = "Pre-condition: Configurator password is blank";

	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;

	/**
	 * Constructor of the class
	 *
	 * @param username Username of Configurator
	 * @param password Password of Configurator
	 */
	public Configurator(
			@JsonProperty("username") String username,
			@JsonProperty("password") String password
	) {
		assert !username.isBlank() : PRE_CONDITION_CONFIGURATOR_USERNAME_IS_BLANK;
		assert !password.isBlank() : PRE_CONDITION_CONFIGURATOR_PASSWORD_IS_BLANK;

		this.username = username;
		this.password = password;
	}

	@Override
	public String getChildType() {
		return "Configurator";
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
