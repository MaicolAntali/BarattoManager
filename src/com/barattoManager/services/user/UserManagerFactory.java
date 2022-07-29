package com.barattoManager.services.user;

import com.barattoManager.services.json.JsonHandler;
import com.barattoManager.utils.AppConfigurator;

public class UserManagerFactory implements Runnable {

	private static UserManager USER_MANAGER;

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
