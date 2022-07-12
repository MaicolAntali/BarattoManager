package com.barattoManager.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.InvalidCredentialsException;
import com.barattoManager.model.user.User;
import com.barattoManager.model.user.configurator.Configurator;
import com.barattoManager.model.user.viewer.Viewer;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.barattoManager.manager.Constants.*;


public final class UserManager implements Manager {

	private final ConcurrentHashMap<String, User> userMap;

	public UserManager(ConcurrentHashMap<String, User> userMap) {
		this.userMap = userMap;
	}

	public void addNewUser(String username, String password, Boolean isAdmin) throws AlreadyExistException, IllegalValuesException {
		if (username.isBlank())
			throw new IllegalValuesException(ERROR_INVALID_USERNAME);

		if (userMap.containsKey(username.toLowerCase()))
			throw new AlreadyExistException(ERROR_USER_ALREADY_EXIST.formatted(username));

		User user;
		if (Objects.requireNonNull(isAdmin, MESSAGE_IMPOSSIBLE_ESTABLISH_USER_INSTANCE)) {
			user = new Configurator(username, password);
		}
		else {
			user = new Viewer(username, password);
		}

		userMap.put(username.toLowerCase(), user);
		EventFactory.getUsersEvent().fireListener(userMap);

		assert userMap.containsKey(username.toLowerCase()) : POST_CONDITION_USER_NOT_IN_MAP;

	}

	public User checkCredential(String username, String password) throws InvalidCredentialsException {
		User user = userMap.get(Objects.requireNonNull(username).toLowerCase());

		if (user == null)
			throw new InvalidCredentialsException(ERROR_USER_NOT_FOUND);
		else {
			if (user.getPassword().equals(password))
				return user;
			else
				throw new InvalidCredentialsException(ERROR_PASSWORD_NOT_MATCH);
		}
	}

	/**
	 * Method used to set password of a User
	 * @param user User object to set the new password
	 * @param password New Password
	 */
	public void setUserPassword(User user, String password) {
		user.setPassword(password);
		EventFactory.getUsersEvent().fireListener(userMap);
	}

	public List<User> getUserList() {
		return userMap.values().stream().toList();
	}
}
