package com.barattoManager.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Class that represent the Viewer object
 */
@JsonTypeName("Viewer")
public final class Viewer extends User {

	private static final String PRE_CONDITION_VIEWER_USERNAME_IS_BLANK = "Pre-condition: Viewer username is blank";
	private static final String PRE_CONDITION_VIEWER_PASSWORD_IS_BLANK = "Pre-condition: Viewer password is blank";

	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;


	/**
	 * Constructor of the class
	 *
	 * @param username Username of Viewer
	 * @param password Password of Viewer
	 */
	public Viewer(
			@JsonProperty("username") String username,
			@JsonProperty("password") String password
	) {
		assert !username.isBlank() : PRE_CONDITION_VIEWER_USERNAME_IS_BLANK;
		assert !password.isBlank() : PRE_CONDITION_VIEWER_PASSWORD_IS_BLANK;

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
