package com.barattoManager.user;

import com.barattoManager.user.configurator.Configurator;
import com.barattoManager.user.viewer.Viewer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "@type")
@JsonSubTypes({
		@JsonSubTypes.Type(value= Configurator.class, name = "Configurator"),
		@JsonSubTypes.Type(value= Viewer.class, name = "Viewer"),
})
public abstract class User {
	@JsonProperty("@type")
	public abstract String getChildType();

	public abstract void setPassword(String password);

	public abstract String getUsername();

	public abstract String getPassword();

	public boolean isPasswordValid(String password) {
		return !Objects.equals(password, "123") && password.length() >= 5;
	}
}
