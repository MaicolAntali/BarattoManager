package com.barattoManager.user.viewer;

import com.barattoManager.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe that represent the Viewer {@code extends User}
 */
@JsonTypeName("Viewer")
public class Viewer extends User {

	@JsonProperty("username")
	private String username;

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
		this.username = username;
		this.password = password;
	}

	/**
	 * Method used to get the child type
	 * @return {@link String} that represents the child type
	 */
	@Override
	public String getChildType() {
		return "Configurator";
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
