package com.barattoManager.ui.mvc.loginUser;

import com.barattoManager.ui.mvc.base.BaseModel;

public class LoginUserModel implements BaseModel {
    private String username;
    private String password;

    public LoginUserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
