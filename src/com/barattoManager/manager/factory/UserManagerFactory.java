package com.barattoManager.manager.factory;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.manager.UserManager;
import com.barattoManager.manager.json.JsonHandler;
import com.barattoManager.model.user.User;
import com.barattoManager.utils.AppConfigurator;

public class UserManagerFactory {

	private static final UserManager USER_MANAGER;

	static {
		var jsonHandler = new JsonHandler<String, User>(
				AppConfigurator.getInstance().getFileName("users")
		);

		USER_MANAGER = new UserManager(jsonHandler.readJson(String.class, User.class));

		EventFactory.getUsersEvent().addListener(jsonHandler);
	}

	public static UserManager getManager() {
		return USER_MANAGER;
	}
}
