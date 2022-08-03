package com.barattoManager.services;

import com.barattoManager.services.user.User;

public class Store {

	private static User loggedUser;

	public static User getLoggedUser() {
		return loggedUser;
	}

	public static void setLoggedUser(User loggedUser) {
		Store.loggedUser = loggedUser;
	}
}
