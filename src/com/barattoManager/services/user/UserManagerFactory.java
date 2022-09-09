package com.barattoManager.services.user;

import com.barattoManager.services.json.JsonHandler;
import com.barattoManager.utils.AppConfigurator;

/**
 * Class that constructs the {@link UserManager}<br/>
 * {@link UserManager}is declared in the class as a static field, to ensure one instance for the whole project.
 * It implements {@link Runnable} because it is run in a separated thread
 */
public class UserManagerFactory implements Runnable {

	private static UserManager USER_MANAGER;

	/**
	 * Method used to get the user manager
	 *
	 * @return USER_MANAGER {@link UserManager}
	 */
	public static UserManager getManager() {
		return USER_MANAGER;
	}

	@Override
	public void run() {
		var jsonHandler = new JsonHandler<String, User>(AppConfigurator.getInstance().getFileName("users"));

		UserUpdateDataEventFactory
				.getEventHandler()
				.addListener(jsonHandler);

		USER_MANAGER = new UserManager(jsonHandler.readJson(String.class, User.class));
	}
}
