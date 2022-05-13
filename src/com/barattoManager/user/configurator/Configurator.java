package com.barattoManager.user.configurator;

import com.barattoManager.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe that represent the Configurator {@code extends User}
 */
@JsonTypeName("Configurator")
public class Configurator extends User {

    /**
     * Configurator
     */
    private static final String CONFIGURATOR = "Configurator";
    /**
     * Name of configurator
     */
    @JsonProperty("username")
    private String username;
    /**
     * Password of configurator
     */
    @JsonProperty("password")
    private String password;

    /**
     * {@link Configurator} constructor
     * @param username Username of configurator
     * @param password Password of configurator
     */
    public Configurator(
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
        return CONFIGURATOR;
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
