package com.barattoManager.services;

import com.barattoManager.services.user.User;

/**
 * Static class used to store useful information
 */
public class Store {

	private static User loggedUser;

	/**
	 * Method used to get the loggedUser
	 *
	 * @return loggedUser {@link User} to get
	 */
	public static User getLoggedUser() {
		return loggedUser;
	}

	/**
	 * Method used to set the loggedUser
	 *
	 * @param loggedUser {@link User} to set
	 */
	public static void setLoggedUser(User loggedUser) {
		Store.loggedUser = loggedUser;
	}
}
