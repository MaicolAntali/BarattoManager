package com.barattoManager.manager.factory;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.manager.UserManager;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.user.User;
import com.barattoManager.utils.AppConfigurator;

/**
 * Class that constructs the {@link UserManager}<br/>
 * {@link UserManager}is declared in the class as a static constant, to ensure one instance for the whole project.
 */
public class UserManagerFactory {

	private static final UserManager USER_MANAGER;

	static {
		var jsonHandler = new JsonHandler<String, User>(
				AppConfigurator.getInstance().getFileName("users")
		);

		EventFactory.getUsersEvent().addListener(jsonHandler);

		USER_MANAGER = new UserManager(jsonHandler.readJson(String.class, User.class));
	}

	/**
	 * Method used to get the instance of {@link UserManager} stored in the class.
	 *
	 * @return The {@link UserManager} object
	 */
	public static UserManager getManager() {
		return USER_MANAGER;
	}
}
