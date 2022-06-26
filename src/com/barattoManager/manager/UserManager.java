package com.barattoManager.manager;

import com.barattoManager.exception.AlreadyExistException;
import com.barattoManager.exception.IllegalValuesException;
import com.barattoManager.exception.InvalidCredentialsException;
import com.barattoManager.model.user.User;
import com.barattoManager.model.user.configurator.Configurator;
import com.barattoManager.model.user.viewer.Viewer;
import com.barattoManager.utils.AppConfigurator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import static com.barattoManager.manager.Constants.*;

/**
 * This class is a <b>Singleton Class</b><br/> used to access from anywhere to the users(Configurator or User).
 */
public final class UserManager extends NoConcurrencyManager<String, User>{

	/**
	 * {@link UserManager} constructor
	 */
	private UserManager() {
		super(String.class, User.class);

		if (getDataMap().isEmpty()) {
			try {
				addNewUser("Configurator", AppConfigurator.getInstance().getPasswordSetting("default_pwd"), true);
				JOptionPane.showMessageDialog(
						null,
						FIRST_CONFIGURATOR_CREDENTIALS.formatted(AppConfigurator.getInstance().getPasswordSetting("default_pwd")),
						DEFAULT_CREDENTIALS,
						JOptionPane.INFORMATION_MESSAGE
				);
			} catch (AlreadyExistException | IllegalValuesException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	File getJsonFile() {
		return new File(AppConfigurator.getInstance().getFileName("user_file"));
	}

	@Override
	ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	@Override
	void afterDataChangeActions() {

	}

	/**
	 * Holder class of instance
	 */
	private static final class UserManagerHolder {
		/**
		 * Instance of {@link UserManager}
		 */
		private static final UserManager instance = new UserManager();
	}


	/**
	 * Method used to create get the {@link UserManager} instance.
	 * This method use the lazy loading mechanism cause the inner class is loaded only if
	 * the {@code getInstance()} method is called.
	 * Also is thread safe cause every thread read the same {@link UserManager} instance.
	 *
	 * @return The Instance of {@link UserManager} class
	 */
	public static UserManager getInstance() {
		return UserManagerHolder.instance;
	}

	/**
	 * Method used to add a new {@link User} <br/>
	 * This method can select which type of user to create:
	 * <ul>
	 *     <li>{@link Configurator} -> If the {@code isAdmin} is {@code true}</li>
	 *     <li>{@link Viewer} -> If the {@code isAdmin} is {@code false}</li>
	 * </ul>
	 * After adding the User in the {@code HashMap<String, User> userMap}, the method calls {@link #saveDataMap()}
	 * for save the last change in the json file.
	 *
	 * @param username Username of the User
	 * @param password Password of the User
	 * @param isAdmin  Indicates whether a User is an admins or not.
	 * @throws AlreadyExistException Is thrown if the new user that are trying to add already exist.
	 * @throws IllegalValuesException Is thrown if the username is empty
	 */
	public void addNewUser(String username, String password, Boolean isAdmin) throws AlreadyExistException, IllegalValuesException {
		if (!username.isBlank()) {
			if (getDataMap().containsKey(username.toLowerCase()))
				throw new AlreadyExistException(ERROR_USER_ALREADY_EXIST.formatted(username));
			else {
				User user;
				if (Objects.requireNonNull(isAdmin, MESSAGE_IMPOSSIBLE_ESTABLISH_USER_INSTANCE)) {
					user = new Configurator(username, password);
				}
				else {
					user = new Viewer(username, password);
				}

				getDataMap().put(username.toLowerCase(), user);
				saveDataMap();

				assert getDataMap().containsKey(username) : POST_CONDITION_USER_NOT_IN_MAP;
			}
		}
		else {
			throw new IllegalValuesException(ERROR_INVALID_USERNAME);
		}

	}

	public User checkCredential(String username, String password) throws InvalidCredentialsException {
		User user = getDataMap().get(Objects.requireNonNull(username).toLowerCase());

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
		saveDataMap();
	}

	public HashMap<String, User> getUserMap() {
		return getDataMap();
	}

}
