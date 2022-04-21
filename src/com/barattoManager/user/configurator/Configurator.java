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

    @JsonProperty("is_admin")
    private Boolean isAdmin;

    public Configurator(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("is_admin") Boolean isAdmin
    ) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
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
