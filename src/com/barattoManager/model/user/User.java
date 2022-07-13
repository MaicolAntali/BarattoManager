package com.barattoManager.model.user;

import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * Abstract class representing the user object<br/>
 * Implement in the {@link Configurator} and {@link Viewer} classes
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "@type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = Configurator.class, name = "Configurator"),
		@JsonSubTypes.Type(value = Viewer.class, name = "Viewer"),
})
public sealed abstract class User permits Configurator, Viewer {

	/**
	 * Method used to get the child type<br/>
	 * <i>Used by Jackson Library during serialization</i>
	 *
	 * @return {@link String} that represents the child type name
	 */
	@JsonProperty("@type")
	public abstract String getChildType();

	/**
	 * Method used to set the user password
	 *
	 * @param password Password to set
	 */
	public abstract void setPassword(String password);

	/**
	 * Method used to get the username
	 *
	 * @return The Username of User
	 */
	public abstract String getUsername();

	/**
	 * Method used to get the password
	 *
	 * @return The password of User
	 */
	public abstract String getPassword();

	/**
	 * Method used to check if a password is valid:
	 * <ul>
	 *     <li>Not Empty</li>
	 *     <li>More that 5 char</li>
	 *     <li>Different from {@code default_pwd}</li>
	 * </ul>
	 *
	 * @param password Password to check
	 * @return True if the password is valid otherwise false
	 */
	public boolean isPasswordValid(String password) {
		return !Objects.equals(password, AppConfigurator.getInstance().getPasswordSetting("default_pwd")) && password.trim().length() >= 5;
	}
}
