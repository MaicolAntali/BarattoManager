package com.barattoManager.model.user.viewer;

import com.barattoManager.model.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Class that represent the Viewer {@code extends User}
 */
@JsonTypeName("Viewer")
public class Viewer extends User {
	/**
	 * Pre-condition: Viewer username is blank
	 */
	public static final String PRE_CONDITION_VIEWER_USERNAME_IS_BLANK = "Pre-condition: Viewer username is blank";
	/**
	 * Pre-condition: Viewer password is blank
	 */
	public static final String PRE_CONDITION_VIEWER_PASSWORD_IS_BLANK = "Pre-condition: Viewer password is blank";
	/**
	 * Username of viewer
	 */
	@JsonProperty("username")
	private String username;

	/**
	 * Password of viewer
	 */
	@JsonProperty("password")
	private String password;


	/**
	 * {@link Viewer} constructor
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

	/**
	 * Method used to get the child type
	 * @return {@link String} that represents the child type
	 */
	@Override
	public String getChildType() {
		return "Viewer";
	}

	/**
	 * Method used to set the User password
	 * @param password Password to set
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Method used to get the username
	 * @return The Username of User
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * Method used to get the password
	 * @return The password of User
	 */
	@Override
	public String getPassword() {
		return password;
	}
}
