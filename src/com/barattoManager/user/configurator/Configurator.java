package com.barattoManager.user.configurator;

import com.barattoManager.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Configurator")
public class Configurator extends User {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public Configurator(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password
    ) {
        this.username = username;
        this.password = password;
    }

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
