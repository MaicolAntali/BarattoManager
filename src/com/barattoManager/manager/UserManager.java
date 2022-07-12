package com.barattoManager.manager;

import com.barattoManager.event.factory.EventFactory;
import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.InvalidCredentialsException;
import com.barattoManager.model.user.User;
import com.barattoManager.model.user.configurator.Configurator;
import com.barattoManager.model.user.viewer.Viewer;
import com.barattoManager.utils.AppConfigurator;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.barattoManager.manager.Constants.*;

/**
 * Class that handles users
 */
public final class UserManager implements Manager {

	private final ConcurrentHashMap<String, User> userMap;

	/**
	 * Constructor of the class<br/>
	 * If the HashMap is empty then it inserts a default user and shows on the screen this operation
	 *
	 * @param userMap {@link ConcurrentHashMap} that will be used by the manager for all the operations it has to perform on the users.
	 */
	public UserManager(ConcurrentHashMap<String, User> userMap) {
		this.userMap = userMap;

		if (userMap.isEmpty()) {
			try {
				addNewUser("Configurator", AppConfigurator.getInstance().getPasswordSetting("default_pwd"), true);
			} catch (AlreadyExistException | IllegalValuesException e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(
					null,
					"Sono state impostate delle credenziali di base per il primo configuratore. \n\nUsername: Configurator\nPassword: %s".formatted(AppConfigurator.getInstance().getPasswordSetting("default_pwd")),
					"Credenziali di default",
					JOptionPane.INFORMATION_MESSAGE
			);
		}
	}

	/**
	 * Method used to add a new user
	 *
	 * @param username Username of the new user
	 * @param password Password of the new user
	 * @param isAdmin If true it will be created a {@link Configurator} otherwise {@link Viewer}
	 * @throws AlreadyExistException Is thrown if the username already exist
	 * @throws IllegalValuesException Is thrown if the username is blank
	 */
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

	/**
	 * methode used to verify a user’s credentials
	 *
	 * @param username Username to check
	 * @param password Password to check
	 * @return {@link User}  otherwise an exception
	 * @throws InvalidCredentialsException Is thrown if the credentials are invalid
	 */
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
	 *method used to set a new password to a user
	 *
	 * @param user     {@link User} to set new password
	 * @param password New Password to set
	 */
	public void setUserPassword(User user, String password) {
		user.setPassword(password);
		EventFactory.getUsersEvent().fireListener(userMap);
	}

	/**
	 * Method used to get the {@link List} with all {@link User users}
	 *
	 * @return {@link List} with all {@link User users}
	 */
	public List<User> getUserList() {
		return userMap.values().stream().toList();
	}
}
