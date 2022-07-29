package com.barattoManager.services.user;

import com.barattoManager.services.json.JsonHandler;
import com.barattoManager.utils.AppConfigurator;

public class UserManagerFactory {

	private static final UserManager USER_MANAGER;

	static {
		var jsonHandler = new JsonHandler<String, User>(AppConfigurator.getInstance().getFileName("users"));

		UserUpdateDataEventFactory
				.getEventHandler()
				.addListener(jsonHandler);

		USER_MANAGER = new UserManager(jsonHandler.readJson(String.class, User.class));
	}

	public static UserManager getManager() {
		return USER_MANAGER;
	}

}
